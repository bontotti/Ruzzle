package it.polito.tdp.Ruzzle.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import it.polito.tdp.Ruzzle.db.DizionarioDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Model {
	private int SIZE = 4;
	private Board board;
	private List<String> dizionario;
	private StringProperty statusText;
	
	Map<Character,Integer> frequencies ;

	public Model() {
		this(4) ;
	}
	
	public Model(int size) {
		this.SIZE = size ;
		
		this.statusText = new SimpleStringProperty();

		this.board = new Board(SIZE);
		DizionarioDAO dao = new DizionarioDAO();
		this.dizionario = dao.listParola();
		statusText.set(String.format("%d parole lette", this.dizionario.size()));
		
		this.frequencies=dao.frequenzeLettere() ;
		int tot = 0 ;
		for(Character let: this.frequencies.keySet()) 
			tot += frequencies.get(let) ;

		// Normalize to 100000
		for(Character let: this.frequencies.keySet()) {
			frequencies.put(let, (int)(frequencies.get(let)*100000L/tot)) ;
		}

		return;
	}

	public List<Pos> trovaParola(String parola) {
		Ricerca ricerca = new Ricerca();
		return ricerca.trovaParola(parola, this.board);
	}

	public void reset() {
		this.board.resetWithFrequencies(frequencies);
		this.statusText.set("Board Reset");
	}

	public Board getBoard() {
		return this.board;
	}

	public final StringProperty statusTextProperty() {
		return this.statusText;
	}

	public final String getStatusText() {
		return this.statusTextProperty().get();
	}

	public final void setStatusText(final String statusText) {
		this.statusTextProperty().set(statusText);
	}

	public List<String> trovaTutte() {
		List<String> trovate = new ArrayList<>();

		for (String parola : dizionario) {
			parola = parola.toUpperCase();
			if (parola.length() > 1) {
				if (trovaParola(parola) != null) {
					trovate.add(parola);
				}
			}
		}
		
		Collections.sort(trovate, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.length()-o2.length();
			}
		}) ;

		return trovate;
	}

}
