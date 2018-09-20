package editingJUnit;

public class Utils {

	//Helps to print a pixel array for visual feedback
		public static void printArray(int[] array, int width) {
			for(int i = 0; i < array.length; i++) {
				if(i % width == 0) {
					System.out.print("\n");
				}
				System.out.print(array[i] + " ");
				
			}
			System.out.println("");
		}
	
}
