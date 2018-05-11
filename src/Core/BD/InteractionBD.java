package Core.BD;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;

import Core.Session.AccountInfo;
import Services.DataUtilities.DataMessage;
import Services.Groups.AbstractGroup;
import Services.Groups.ChatGroup;
import Services.Groups.PublicationPool;
import Services.Posts.Comment;
import Services.Posts.Publication;

public class InteractionBD {
	private String user_table;
	private String data_table;
	private String pools_table;
	private String user_pool_table;
	private String group_data_table;
	private String posts_table;
	private String pool_post_table;
	private String comments_table;
	



	private EngineBD engine;

	public InteractionBD(String user_table, String data_table) {
		this.user_table = user_table;
		this.data_table = data_table;
		this.engine = new EngineBD(ConnectionBD.getConnection());

	}

	public boolean lookUser(AccountInfo details) throws SQLException {

		ArrayList<Tuple> ts = engine.getData(user_table, details.getP_pseudo());

		if (ts.size() == 1)
			return true;
		else
			return false;
	}

	public boolean identify(AccountInfo details) throws SQLException {
		
		ArrayList<Tuple> ts = engine.getData(user_table, details.getP_pseudo(), details.getP_password());

		if (ts.size() == 1)
			return true;
		else
			return false;
	}

	public void addAccount(AccountInfo details) throws SQLException {

		engine.addData(this.user_table, new Tuple(details.getPseudo(), details.getPassword()));
	}

	
	public ArrayList<DataMessage> loadDataMessages(AccountInfo details) throws SQLException, ParseException {

		DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);

		ArrayList<DataMessage> dm_list = new ArrayList<DataMessage>();
		ArrayList<Tuple> ts = engine.getData(this.data_table, details.getP_pseudo());

		for (Tuple t : ts) {
			dm_list.add(
					new DataMessage((String) t.getValue(1), df.parse((String) t.getValue(2)), (String) t.getValue(3)));
		}
		
		Collections.sort(dm_list, new Comparator<DataMessage>() {
			  public int compare(DataMessage o1, DataMessage o2) {
			      return o1.getDate().compareTo(o2.getDate());
			  }
		});

		return dm_list;

	}

	public void storeIntoBD(String pseudo, DataMessage dt) throws SQLException, ParseException {
		engine.addData(data_table, new Tuple(pseudo, dt.getId_sender(), dt.getDate().toString(), dt.getData()));
	}

	
	public HashMap<String, AbstractGroup> loadGroups() throws SQLException, ParseException {

		HashMap<String, AbstractGroup> pool_list = new HashMap<String, AbstractGroup>();
		ArrayList<Tuple> ts = engine.getData(this.pools_table);

		String pool_name;
		for (Tuple t : ts) {
		
			pool_name = (String) t.getValue(1);
			
			if( ((String) t.getValue(2)).equals("POST")) {
				pool_list.put(pool_name, new PublicationPool(pool_name) );
			}
			else pool_list.put(pool_name, new ChatGroup(pool_name) );
					
		}
		return pool_list;
	}
	
	
	public ArrayList<AccountInfo> loadUsersGroup(String group_name) throws SQLException, ParseException {
		
		ArrayList<Tuple> ts  = engine.getData(this.user_pool_table, new Predicat("GROUPNAME" , group_name));
		ArrayList<AccountInfo> acs = new ArrayList<AccountInfo>();
		
		for(Tuple t : ts) {
			acs.add(new AccountInfo(new Predicat("PSEUDO" , (String)t.getValue(1))));
		}
		
		return acs;
	}
	
	public ArrayList<DataMessage> loadGroupMess(String group_name) throws SQLException, ParseException {
		
		DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);

		ArrayList<DataMessage> dm_list = new ArrayList<DataMessage>();
		  
		ArrayList<Tuple> ts = engine.getData(this.group_data_table, new Predicat("GROUPNAME" , group_name));

	
		for (Tuple t : ts) {
			dm_list.add(
					new DataMessage((String) t.getValue(2), df.parse((String) t.getValue(3)), (String) t.getValue(4)));
		}
		
		Collections.sort(dm_list, new Comparator<DataMessage>() {
			  public int compare(DataMessage o1, DataMessage o2) {
			      return o1.getDate().compareTo(o2.getDate());
			  }
		});

		return dm_list;
	}
	
	public HashMap<UUID, Publication> loadPostsPool(String group_name) throws SQLException, ParseException {
		
		DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);

		HashMap<UUID, Publication> pub_map = new HashMap<UUID, Publication>();
		  
		String joinBD = this.posts_table + " natural join " + this.pool_post_table;
		ArrayList<Tuple> ts = engine.getData(joinBD, new Predicat("GROUPNAME" , group_name));

		
		Publication p = null;
		for (Tuple t : ts) {
			p = new Publication(new AccountInfo(new Predicat("PSEUDO",(String)t.getValue(2))), (String)t.getValue(3), (String)t.getValue(4));
			p.setDate(df.parse((String) t.getValue(5)));
			pub_map.put((UUID)t.getValue(1),p);
		}
		return pub_map;
	}
	
	
	public HashMap<UUID, Comment> loadComments(UUID post_id) throws SQLException, ParseException {
		
		DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);

		HashMap<UUID, Comment> com_map = new HashMap<UUID, Comment>();
		  
		ArrayList<Tuple> ts = engine.getData(this.comments_table, new Predicat("IDPOST" , post_id));

		Comment c = null;
		for (Tuple t : ts) {
			c = new Comment(new AccountInfo(new Predicat("PSEUDO",(String)t.getValue(2))), (String)t.getValue(4));
			c.setDate(df.parse((String) t.getValue(5)));
			com_map.put((UUID)t.getValue(1),c);
		}
		return com_map;
	}
	
	
	
	
	
	
	public boolean lookPool(String g_name) throws SQLException {
		ArrayList<Tuple> ts = engine.getData(this.pools_table, new Predicat("POOLNAME" , g_name));
		if (ts.size() == 1)
			return true;
		else
			return false;
	}
	
	
	
		
	public void addPool(String g_name , String pool_type) throws SQLException{
		
		this.engine.addData(this.pools_table, new Tuple(g_name, pool_type));
			
	}
	
	
	
	
	
	
	/** --------------------------------------------------------- **/
	public void setPools_table(String pools_table) {
		this.pools_table = pools_table;
	}

	public void setUser_pool_table(String user_pool_table) {
		this.user_pool_table = user_pool_table;
	}

	public void setGroup_data_table(String group_data_table) {
		this.group_data_table = group_data_table;
	}

	public void setPosts_table(String posts_table) {
		this.posts_table = posts_table;
	}

	public void setPool_post_table(String pool_post_table) {
		this.pool_post_table = pool_post_table;
	}

	public void setComments_table(String comments_table) {
		this.comments_table = comments_table;
	}
}
