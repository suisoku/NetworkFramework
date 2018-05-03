package Services.Profile;


import java.util.Iterator;

public class Tuple implements InterfaceTuple {
	protected final Object[] valeurs;

	public Tuple(Object... valeurs) {
		this.valeurs = valeurs;
	}

	
	@Override
	public Iterator<Object> iterator() {
		return new Iterator<Object>() {
			private int index = 0;

			@Override
			public boolean hasNext() {
				return index < valeurs.length;
			}

			@Override
			public Object next() {
				return valeurs[index++];
			}
		};
	}

	


	
	

}

	