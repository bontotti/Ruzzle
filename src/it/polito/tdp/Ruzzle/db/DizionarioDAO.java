package it.polito.tdp.Ruzzle.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DizionarioDAO {
	
	/**
	 * Ritorna tutte le parole presenti nel dizionario
	 * 
	 * @return
	 */
	public List<String> listParola() {
		List<String> result = new ArrayList<>() ;
		
		String query = "SELECT nome FROM parola ORDER BY nome" ;
		
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(query) ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				result.add(res.getString("nome")) ;
			}
			
			res.close();
			conn.close();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result ;

	}
	
	public Map<Character,Integer> frequenzeLettere() {
		
		Map<Character,Integer> freqs = new HashMap<Character, Integer>() ;
		
		
		try {
			Connection conn = DBConnect.getConnection() ;
			
			for(char letter = 'A'; letter <='Z'; letter++) {
				
				String query = "SELECT COUNT(id) AS c " + 
						"FROM parola " + 
						"WHERE nome LIKE '%"+letter+"%'" ;
				PreparedStatement st = conn.prepareStatement(query) ;

				ResultSet res = st.executeQuery() ;
				
				if(res.next()) {
					freqs.put(letter, res.getInt("c")) ;
				}

				res.close();
			}			
			
			conn.close();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return freqs ;
		
	}

}
