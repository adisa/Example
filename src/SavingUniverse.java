import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;


public class SavingUniverse {
	public static void main(String[] args) {
		
		try {
			FileInputStream fin = new FileInputStream("C:\\Users\\Adisa\\workspace\\2008_Qual\\src\\A-large-practice.in");
			
			DataInputStream in = new DataInputStream(fin);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			int n = Integer.parseInt(br.readLine());
			
			for (int i = 0; i < n; i ++) {
				
				int s = Integer.parseInt(br.readLine());
				
				HashMap<String, Boolean> engines = new HashMap<String, Boolean>(s);

				for (int j = 0; j < s; j++) {
					engines.put(br.readLine(), false);
				}
				
				int q = Integer.parseInt(br.readLine());
				
				int count = 0;
				int switches = 0;
				
				for (int j = 0; j < q; j++) {
					String query = br.readLine();
					
					if (engines.containsKey(query)) {
						
						if (!engines.get(query)) {
							count++;
						}												
						
						if (count == s) {
							switches++;
							
							Iterator<String> it = engines.keySet().iterator();
							
							while (it.hasNext()) {
								String key = (String) it.next();
								engines.put(key, false);
							}
							
							count = 1;
						}
						
						engines.put(query, true);
					}
				}
				
				System.out.println("Case #"  + Integer.toString(i+1) + ": " + Integer.toString(switches));
			}
			
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
