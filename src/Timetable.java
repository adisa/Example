import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Timetable {
	
	public static int turnaround;

	public static void main(String[] args) {
		
		try {
			FileInputStream fin = new FileInputStream("C:\\Users\\Adisa\\workspace\\2008_Qual\\src\\B-large-practice.in");
			
			DataInputStream in = new DataInputStream(fin);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			int N = Integer.parseInt(br.readLine());
			
			for (int i = 0; i < N; i++) {
				
				turnaround = Integer.parseInt(br.readLine());
				
				String[] temp = br.readLine().split(" ");
				int NA = Integer.parseInt(temp[0]);
				int NB = Integer.parseInt(temp[1]);
				
				int[] departA = new int[NA];
				int[] tempB = new int[NA];
				int[] departB = new int[NB];
				int[] tempA = new int[NB];
				
				for (int j = 0; j < NA; j++) {
					String[] times = br.readLine().split(" ");
					String[] d = times[0].split(":");
					String[] a = times[1].split(":");
					
					departA[j] = 60 * Integer.parseInt(d[0]) + Integer.parseInt(d[1]);
					tempB[j] = 60 * Integer.parseInt(a[0]) + Integer.parseInt(a[1]);
				}
				
				for (int j = 0; j < NB; j++) {
					String[] times = br.readLine().split(" ");
					String[] d = times[0].split(":");
					String[] a = times[1].split(":");
					
					departB[j] = 60 * Integer.parseInt(d[0]) + Integer.parseInt(d[1]);
					tempA[j] = 60 * Integer.parseInt(a[0]) + Integer.parseInt(a[1]);
				}
				
				departA = radixSort(departA);
				tempB = radixSort(tempB);
				departB = radixSort(departB);
				tempA = radixSort(tempA);
				
				List<Integer> arriveB = new ArrayList<Integer>(NA);
				List<Integer> arriveA = new ArrayList<Integer>(NB);
				
				for (int j = 0; j < NA; j++) {
					arriveB.add(tempB[j]);
				}
				
				for (int j = 0; j < NB; j++) {
					arriveA.add(tempA[j]);
				}
				
				int trainsA = 0;
				int trainsB = 0;
				
				for (int j = 0; j < NA; j++) {
					
					if (arriveA.size() > 0) {
						if (arriveA.get(0) > departA[j] - turnaround) {
							trainsA++;
						} else {
							arriveA.remove(0);
						}
					} else {
						trainsA++;
					}
				}
				
				for (int j = 0; j < NB; j++) {
					
					if (arriveB.size() > 0) {
						if (arriveB.get(0) > departB[j] - turnaround) {
							trainsB++;
						} else {
							arriveB.remove(0);
						}
					} else {
						trainsB++;
					}
				}
				
				//System.out.println(Arrays.toString(tempB));
				//System.out.println(Arrays.toString(tempA));
				System.out.println("Case #"  + Integer.toString(i+1) + ": " + Integer.toString(trainsA) + " " + Integer.toString(trainsB));				
			}			
			
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static int findTrain(int departure, List<Integer> arrivals, int lo, int hi) { //modified binary search. returns index of prev arrival or -1 if not found
		int size = hi - lo + 1;
		
		if (size == 1) {
			
			if (arrivals.get(lo) < departure - turnaround){
				return lo;
			} else {
				return - 1;
			}
		}
		
		int mid = (lo + hi)/2;
		
		if (arrivals.get(mid) >  departure - turnaround){
			
			return findTrain(departure, arrivals, lo, mid);
		} else if (arrivals.get(mid + 1) <= departure - turnaround) {
			
			return findTrain(departure, arrivals, mid + 1, hi);
		} else { //arrivals(size/2) < d-5 but arrivals(size/2) greater
			
			return mid/2;
		}
	}
	
	public static int[] radixSort(int[] list) {
		int length = list.length;
		
		int[] sorted = Arrays.copyOf(list, length);
		
		int[] temp = new int[length];
		int[] digits = new int[length];
		int[] indices = new int[length];
		
		for (int i = 0; i < 4; i++) {			
			
			for (int j = 0; j < length; j++) {
				digits[j] = (int) (sorted[j]/Math.pow(10.0,i/1.0) % 10); //get j + 1 th digit from right
			}
			
			indices = countingSortIndices(digits);
			
			for (int j = 0; j < length; j++) {
				temp[j] = sorted[indices[j]];
			}
			
			sorted = Arrays.copyOf(temp, length);
		}

		return sorted;
	}
	
	public static int[] countingSortIndices(int[] list){
		int[] count = new int[10];
		
		for (int i = 0; i < list.length; i++) {			
			count[list[i]] += 1;
		}
		
		int countCum = 0;
		
		for (int i = 0; i < 10; i++) {
			countCum += count[i];
			count[i] = countCum;
		}
		
		int[] sorted = new int[list.length];
		
		for (int i = list.length - 1; i > -1; i--) {
			sorted[count[list[i]] - 1] = i;
			count[list[i]] -= 1;
		}
		
		return sorted;
	}

}

/*class SortedList {
	int[] list;
	int[] indices;
	
	public SortedList(int[] list, int[] indices) {
		this.list = list;
		this.indices = indices;
	}
}*/
