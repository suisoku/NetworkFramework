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
import Services.Profile.Profile;

public class InteractionBD {
	private String user_table;
	private String data_table;
	private String pools_table;
	private String user_pool_table;
	private String group_data_table;
	private String posts_table;
	private String pool_post_table;
	private String comments_table;
	private String profile_table;
	
	private ArrayList<String> user_schema;
	@SuppressWarnings("unused")
	private ArrayList<String> data_schema;
	private ArrayList<String> pools_schema;
	private ArrayList<String> user_pool_schema;
	@SuppressWarnings("unused")
	private ArrayList<String> group_data_schema;
	private ArrayList<String> posts_schema;
	private ArrayList<String> pool_post_schema;
	private ArrayList<String> comments_schema;
	private ArrayList<String> profile_schema;


	private EngineBD engine;

	public InteractionBD(String user_table, String data_table) {
		this.user_table = user_table;
		this.data_table = data_table;
		this.engine = new EngineBD(ConnectionBD.getConnection());

	}


	public void setProfile_table(String profile_table) {
		this.profile_table = profile_table;
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
		
			pool_name = (String) t.getValue(0);
			
			if( ((String) t.getValue(1)).equals("POST")) {
				pool_list.put(pool_name, new PublicationPool(pool_name) );
			}
			else pool_list.put(pool_name, new ChatGroup(pool_name) );
					
		}
		return pool_list;
	}
	
	
	public ArrayList<AccountInfo> loadUsersGroup(String group_name) throws SQLException, ParseException {
		
		
		
		ArrayList<Tuple> ts  = engine.getData(this.user_pool_table, new Predicat(user_pool_schema.get(1) , group_name));
		ArrayList<AccountInfo> acs = new ArrayList<AccountInfo>();
		
		for(Tuple t : ts) {
			acs.add(new AccountInfo(new Predicat(this.user_schema.get(0) , (String)t.getValue(0))));
		}
		
		return acs;
	}
	
	public ArrayList<DataMessage> loadGroupMess(String group_name) throws SQLException, ParseException {
		
		DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);

		ArrayList<DataMessage> dm_list = new ArrayList<DataMessage>();
		  
		ArrayList<Tuple> ts = engine.getData(this.group_data_table, new Predicat(this.user_pool_schema.get(1), group_name));

	
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
	
	public HashMap<UUID, Publication> loadPostsPool(String group_name) throws SQLException, ParseException {
		
		DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);

		HashMap<UUID, Publication> pub_map = new HashMap<UUID, Publication>();
		  
		String joinBD = this.posts_table + " natural join " + this.pool_post_table;
		ArrayList<Tuple> ts = engine.getData(joinBD, new Predicat(this.user_pool_schema.get(1) , group_name));

		
		Publication p = null;
		for (Tuple t : ts) {
			p = new Publication(new AccountInfo(new Predicat(user_schema.get(0),(String)t.getValue(1))), 
					(String)t.getValue(2), 
					(String)t.getValue(3),
					UUID.fromString((String) t.getValue(0)));
			
			p.setDate(df.parse((String) t.getValue(4)));
			
			pub_map.put(UUID.fromString((String) t.getValue(0)),p);
		}
		return pub_map;
	}
	
	
	public HashMap<UUID, Comment> loadComments(UUID post_id) throws SQLException, ParseException {
		
		DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);

		HashMap<UUID, Comment> com_map = new HashMap<UUID, Comment>();
		  
		ArrayList<Tuple> ts = engine.getData(this.comments_table, new Predicat(this.comments_schema.get(2) , post_id));

		Comment c = null;
		for (Tuple t : ts) {
			c = new Comment(new AccountInfo(new Predicat(user_schema.get(0),
									(String)t.getValue(1))), 
									(String)t.getValue(3),
									UUID.fromString((String) t.getValue(0)));
			
			c.setDate(df.parse((String) t.getValue(4)));
			com_map.put(UUID.fromString((String) t.getValue(0)),c);
		}
		return com_map;
	}
	
	
	
	
	
	
	public boolean lookPool(String g_name) throws SQLException {
		ArrayList<Tuple> ts = engine.getData(this.pools_table, new Predicat(this.pools_schema.get(0) , g_name));
		if (ts.size() == 1)
			return true;
		else
			return false;
	}
	public void addPool(String g_name , String pool_type) throws SQLException{
		
		this.engine.addData(this.pools_table, new Tuple(g_name, pool_type));
			
	}
	/** --------------------------------------------------------------- 
	 * @throws SQLException */
	
	
	public void addUserPool(AccountInfo u , String g_name) throws SQLException  {
		this.engine.addData(this.user_pool_table, new Tuple(u.getPseudo(), g_name));
	}
	
	public void removeUserPool(AccountInfo u , String g_name) throws SQLException {
		this.engine.deleteData(this.user_pool_table, u.getP_pseudo() , new Predicat(this.user_pool_schema.get(1),g_name));
	}

	
	public void addPost(Publication post , String g_name)throws SQLException{
		this.engine.addData(
		this.posts_table, 
		new Tuple(post.getIdPost().toString(),
				  post.getUser().getPseudo(), 
				  post.getTitlePost(),
				  post.getTextField(),
				  post.getDatePost().toString()
				));

		this.engine.addData(this.pool_post_table, new Tuple(post.getIdPost().toString(), g_name));
	}
	
	public void removePost(UUID post ) throws SQLException {
		this.engine.deleteData(this.pool_post_table, new Predicat(pool_post_schema.get(0),post));
		this.engine.deleteData(this.posts_table, new Predicat(posts_schema.get(0),post));
	}

	public void addComment(Comment c , UUID idpost) throws SQLException {
		this.engine.addData(this.comments_table, 
			new Tuple(c.getIdPost().toString(),
					  c.getUser().getPseudo(),
					  idpost.toString(),
					  c.getTextField(),
					  c.getDatePost().toString()
					));
	}
	
	public void removeComment(UUID c)throws SQLException {
		this.engine.deleteData(this.comments_table, new Predicat(comments_schema.get(0),c));
	}
	
	
	public void addmessGroup(DataMessage dt , String g_name) throws SQLException {
		this.engine.addData(this.group_data_table, 
			new Tuple(g_name,
					  dt.getId_sender(),
					  dt.getDate().toString(),
					  dt.getData()
					));
	}
	
	
	
	public Profile getProfile(AccountInfo a) throws SQLException {
		ArrayList<Tuple> ts =  this.engine.getData(profile_table , new Predicat(this.profile_schema.get(0), a.getPseudo()));
		
		ArrayList<Object> to = new ArrayList<Object>();
		for(int i = 0 ; i < ts.get(0).getSize(); i++) {
			to.add(ts.get(0).getValue(i));
		}
		
		return new Profile(a , to);
	}
	
	public void addProfile(Profile p) throws SQLException {
		Tuple t = new Tuple(p.getListElements().toArray());
		this.engine.addData(this.profile_table, t);
	}
	
	public void editProfile(Profile p) throws SQLException {
		
		HashMap<String , Object> mapP = new HashMap<String, Object>();
		HashMap<String , Object> mapSelect  = new HashMap<String, Object>();
		
		mapSelect.put(this.profile_schema.get(0), p.getOwner().getPseudo());
		
		for(int i = 1 ; i < p.getListElements().size() ; i++) {
			mapP.put(profile_schema.get(i), p.getListElements().get(i));
		}
		this.engine.updateData(profile_table, new Predicat(mapSelect , mapP));
	}
	
	public void editPost(UUID idpost , String m) throws SQLException {
		// En construction
	}
	public void editComment(UUID idc , String m) throws SQLException {
		// En construction
	}

	// Special service for gui needs 
	
	public ArrayList<AccountInfo> getAllUsers() throws SQLException{
		
		ArrayList<AccountInfo>  ai = new ArrayList<AccountInfo>(); 
		
		for(Tuple t : this.engine.getData(this.user_table)) {
			ai.add(new AccountInfo(new Predicat(this.user_schema.get(0), t.getValue(0))));
		}
		return ai;
	}


	/** --------------------------------------------------------- 
	 * @throws SQLException **/
	
	public void creationsSchema() throws SQLException {
		this.user_schema = engine.getSchema(user_table);
		this.data_schema = engine.getSchema(data_table);
		this.pools_schema = engine.getSchema(pools_table);
		this.user_pool_schema = engine.getSchema(user_pool_table);
		this.group_data_schema = engine.getSchema(group_data_table);
		this.posts_schema = engine.getSchema(posts_table);
		this.pool_post_schema = engine.getSchema(pool_post_table);
		this.comments_schema = engine.getSchema(comments_table);
		this.profile_schema = engine.getSchema(profile_table);
		
	}
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
	

	/** --------------------------------------------------- **/
	
	public EngineBD getEngine() {
		return engine;
	}
}
