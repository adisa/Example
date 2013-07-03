import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class FlySwatter {
	public static double radius;
	public static double r;

	public static void main(String[] args) {
		
		try {
			FileInputStream fin = new FileInputStream("C:\\Users\\Adisa\\workspace\\2008_Qual\\src\\C-large-practice.in");
			
			DataInputStream in = new DataInputStream(fin);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			int N = Integer.parseInt(br.readLine());
			System.out.println(Integer.toString(N));
			
			for (int i = 0; i < N; i++){
				String[] data = br.readLine().split(" ");
				
				double F = Double.parseDouble(data[0]);
				double R = Double.parseDouble(data[1]);
				radius = R - Double.parseDouble(data[2]) - F; //radius within which fly can be possibly caught
				r = Double.parseDouble(data[3]);
				double g = Double.parseDouble(data[4]);
				
				if (2 * F > g || radius < 0) {
					System.out.println("Case #" + Integer.toString(i + 1) + ": 1.000000");
					continue;
				}
				
				double totalArea = Math.PI * Math.pow(R, 2);
				
				double escape = 0.0;
				
				double x = r;
				
				while (x  + F < radius) {
					double y = r;
					
					while (Math.sqrt(Math.pow(x + F, 2.0) + Math.pow(y + F, 2.0)) < radius) {					
						escape += escapeArea(x + F, y + F, x + g - F, y + g - F);
						
						y += (2 * r + g);
					}
					x += (2 * r + g);
				}
				 
				double catchFly = (totalArea - 4 * escape) / totalArea;
				
				System.out.println("Case #" + Integer.toString(i + 1) + ": " + Double.toString(catchFly));
			}
			
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static double escapeArea(double x1, double y1, double x2, double y2) {
		
		if (Math.sqrt(Math.pow(x2, 2.0) + Math.pow(y2, 2.0)) <= radius) {
			
			return (x2 - x1) *  (y2 - y1);
		
		}
		
		double yCross = Math.sqrt(Math.pow(radius, 2.0) - Math.pow(x1, 2.0));
		
		double xCross = Math.sqrt(Math.pow(radius, 2.0) - Math.pow(y1, 2.0));
		
		double a1 = 0.0; double a2 = 0.0;
		
		
		if (y2 > yCross) {
			
			if (x2 > xCross) {
				
				a1 = (xCross - x1) * (yCross - y1) / 2;
				a2 = curvedArea(Math.sqrt(Math.pow(xCross - x1, 2.0) + Math.pow(yCross - y1, 2.0)));
				
			} else {
				
				double yCross2 = Math.sqrt(Math.pow(radius, 2.0) - Math.pow(x2, 2.0));
				
				a1 = (x2 - x1) * (yCross2 - y1 + yCross - y1) / 2;
				a2 = curvedArea(Math.sqrt(Math.pow(x2 - x1, 2.0) + Math.pow(yCross - yCross2, 2.0)));
			}
			
		} else { //y2 <= radius
			
			if (x2 > xCross) {
				
				double xCross2 = Math.sqrt(Math.pow(radius, 2.0) - Math.pow(y2, 2.0));
				
				a1 = (y2 - y1) * (xCross2 - x1 + xCross - x1) / 2;
				a2 = curvedArea(Math.sqrt(Math.pow(xCross - xCross2, 2.0) + Math.pow(y2 - y1, 2.0)));
				
			} else {
				
				double yCross2 = Math.sqrt(Math.pow(radius, 2.0) - Math.pow(x2, 2.0));
				double xCross2 = Math.sqrt(Math.pow(radius, 2.0) - Math.pow(y2, 2.0));
				
				a1 = (x2 - x1) *  (y2 - y1) - (x2 - xCross2) * (y2 - yCross2) / 2;
				a2 = curvedArea(Math.sqrt(Math.pow(x2 - xCross2, 2.0) + Math.pow(y2 - yCross2, 2.0)));
			}
		}
		
		return a1 + a2;
	}
	
	public static double stringArea(double longDist, double shortDist) {
		
		double longChord = chordLength(longDist);
		
		if (shortDist > radius) {
			return curvedArea(longChord);
		}
		
		double shortCord = chordLength(shortDist);
		
		double rectArea = shortCord * 2 * r;
		
		double curvedArea;
		
		if (longDist == 0) {
			curvedArea = 2 * curvedArea(Math.sqrt(Math.pow(r, 2.0) + Math.pow((longChord - shortCord)/2, 2.0)));
			
			return rectArea/2 + curvedArea;
		} else {
			curvedArea = 2 * curvedArea(Math.sqrt(Math.pow(2 * r, 2.0) + Math.pow((longChord - shortCord)/2, 2.0)));
			
			return rectArea + curvedArea;
		}
				
	}
	
	public static double chordLength(double dist) {
		return 2 * Math.sqrt(Math.pow(radius, 2.0) - Math.pow(dist, 2.0));
	}
	
	public static double curvedArea(double chord) {
		
		double theta = 2 * Math.asin(chord/(2 * radius));
		
		double triangle = Math.pow(radius, 2.0) * Math.sin(theta) / 2;
		
		double sector = Math.pow(radius, 2.0) * Math.abs(theta) / 2;

		return sector - triangle;
	}

}
