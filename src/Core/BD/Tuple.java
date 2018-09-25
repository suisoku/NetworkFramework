package Core.BD;


import java.io.Serializable;
import java.util.Iterator;

public class Tuple implements _Tuple,Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected final Object[] valeurs;

	public Tuple(Object... valeurs) {
		this.valeurs = valeurs;
	}
	@Override
	public Object getValue(int indice) {
		return this.valeurs[indice];
	}
	
	public void setValue(Object o, int i) {
		this.valeurs[i] = o;
	}
	
	@Override
	public int getSize() {
		return this.valeurs.length;
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

	