import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Arrays;

public class sorting {
    
    private static int[] arr;
    private static int[] arrCopy;
    private static BufferedReader read;
    private static Random randomGenerator;
    
    private static int size;
    private static int random;
    
    private static int n;

    private static void printArray() {
    	System.out.print("[" + arr[0]);
        for(int i=1; i<size; i++) {
            System.out.print(", " + arr[i]);
        }
        System.out.println("]");
    }
    
    public static void buildheap(){
        n=arr.length-1;
        for(int i=n/2;i>=0;i--){
            heapify(i);
        }
    }
    
    public static void heapify(int i){ 
        int largest;
        int left=2*i;
        int right=2*i+1;
        if(left <= n && arr[left] > arr[i]){
            largest=left;
        }
        else{
            largest=i;
        }
        
        if(right <= n && arr[right] > arr[largest]){
            largest=right;
        }
        if(largest!=i){
            exchange(i,largest);
            heapify(largest);
        }
    }
    
    public static void exchange(int i, int j){
        int t=arr[i];
        arr[i]=arr[j];
        arr[j]=t; 
   }
    
    public static void heapsort(){
        buildheap();    
        for(int i=n;i>0;i--){
            exchange(0, i);
            n=n-1;
            heapify(0);
        }
    }
    
    //Problem 1: Implement the insertion sort algorithm which can sort the subarray of the array arr (from low to high, inclusive)
    private static void insertSort(int low, int high) {
    		int temp;
    		for (int i = low+1; i <= high; i++){
    			temp = arr[i];
    			int j = i - 1;
    			while (j >= low && arr[j] > temp){
    				arr[j+1] = arr[j];
    				j--;
    			}
    			arr[j+1] = temp;
    		}
    }
    
    //Problem 2: Implement the following method which checks if the subarray of the array arr (from low to high, inclusive) is already sorted
    private static boolean isSorted (int low, int high){
    		for (int i = low; i < high; i++)
    			if (arr[i] > arr[i+1])
    				return false;

    		return true;
    }
    
    private static void mergesort(int low, int high) {
        // Check if low is smaller then high, if not then the array is sorted
        if (low < high) {
          // Get the index of the element which is in the middle
          int middle = low + (high - low) / 2;
          // Sort the left side of the array
          mergesort(low, middle);
          // Sort the right side of the array
          mergesort(middle + 1, high);
          // Combine them both
          merge(low, middle, high);
        }
      }

      private static void merge(int low, int middle, int high) {

        // Copy both parts into the arrCopy array
        for (int i = low; i <= high; i++) {
          arrCopy[i] = arr[i];
        }

        int i = low;
        int j = middle + 1;
        int k = low;
        // Copy the smallest values from either the left or the right side back
        // to the original array
        while (i <= middle && j <= high) {
          if (arrCopy[i] <= arrCopy[j]) {
            arr[k] = arrCopy[i];
            i++;
          } else {
            arr[k] = arrCopy[j];
            j++;
          }
          k++;
        }
        // Copy the rest of the left side of the array into the target array
        while (i <= middle) {
          arr[k] = arrCopy[i];
          k++;
          i++;
        }

      }
      
      private static void quicksort(int low, int high) {
    	    int i = low, j = high;
    	    // Get the pivot element from the middle of the list
    	    int pivot = arr[(high+low)/2];

    	    // Divide into two lists
    	    while (i <= j) {
    	      // If the current value from the left list is smaller then the pivot
    	      // element then get the next element from the left list
    	      while (arr[i] < pivot) {
    	        i++;
    	      }
    	      // If the current value from the right list is larger then the pivot
    	      // element then get the next element from the right list
    	      while (arr[j] > pivot) {
    	        j--;
    	      }

    	      // If we have found a values in the left list which is larger then
    	      // the pivot element and if we have found a value in the right list
    	      // which is smaller then the pivot element then we exchange the
    	      // values.
    	      // As we are done we can increase i and j
    	      if (i < j) {
    	        exchange(i, j);
    	        i++;
    	        j--;
    	      } else if (i == j) { i++; j--; }
    	    }

    	    // Recursion
    	    if (low < j)
    	      quicksort(low, j);
    	    if (i < high)
    	      quicksort(i, high);
    	  }

      //calculate the median of three elements
      private static int median(int a, int b, int c){
      		int[] num={a, b, c};
      		Arrays.sort(num);
      		
      		return num[num.length/2];
      }
      
      //Problem 3: (1) (a), (b), (d)
      private static void quicksort1(int low, int high){
      		if ((high - low) < 100)
      				insertSort(low, high);
      		else{
		      		int i = low, j = high;

			  	    int pivot = median(arr[low], arr[(low+high)/2], arr[high]);
			
			  	    while (i <= j) {
			  	      while (arr[i] < pivot) {
			  	        i++;
			  	      }
		
			  	      while (arr[j] > pivot) {
			  	        j--;
			  	      }
			
			  	      if (i < j) {
			  	        exchange(i, j);
			  	        i++;
			  	        j--;
			  	      } else if (i == j) { i++; j--; }
			  	    }
			
			  	    if(!isSorted(low, high)){
					  	    if (low < j)
					  	      quicksort1(low, j);
					  	    if (i < high)
					  	      quicksort1(i, high);
			  	    }
	  	    }
      }
      
      //Problem 3: (2) (a), (c), (d)
      private static void quicksort2(int low, int high){
      		if (size < 100)
      				insertSort(low, high);
      		else{
		      		int i = low, j = high;

			  	    int pivot = median(median(arr[low], arr[(high-low)/6+low], arr[(high-low)/3+low]), median(arr[(high-low)/3+low], arr[(high-low)/2+low], arr[2*(high-low)/3+low]), median(arr[2*(high-low)/3+low], arr[5*high/6+low/6], arr[high]));

			  	    while (i <= j) {
			  	      while (arr[i] < pivot) {
			  	        i++;
			  	      }
		
			  	      while (arr[j] > pivot) {
			  	        j--;
			  	      }
			
			  	      if (i < j) {
			  	        exchange(i, j);
			  	        i++;
			  	        j--;
			  	      } else if (i == j) { i++; j--; }
			  	    }
			
			  	    if(!isSorted(low, high)){
					  	    if (low < j)
					  	      quicksort2(low, j);
					  	    if (i < high)
					  	      quicksort2(i, high);
			  	    }
	  	    }
      }
      
      //Problem 3: (3) (b), (d)
      private static void quicksort3(int low, int high){
		      		int i = low, j = high;

			  	    int pivot = median(arr[low], arr[(low+high)/2], arr[high]);
			
			  	    while (i <= j) {
			  	      while (arr[i] < pivot) {
			  	        i++;
			  	      }
		
			  	      while (arr[j] > pivot) {
			  	        j--;
			  	      }
			
			  	      if (i < j) {
			  	        exchange(i, j);
			  	        i++;
			  	        j--;
			  	      } else if (i == j) { i++; j--; }
			  	    }
			
			  	    if(!isSorted(low, high)){
					  	    if (low < j)
					  	      quicksort3(low, j);
					  	    if (i < high)
					  	      quicksort3(i, high);
			  	    }
      }
      
      //Problem 3: (4) (a), (b)
      private static void quicksort4(int low, int high){
      		if (size < 100)
      				insertSort(low, high);
      		else{
		      		int i = low, j = high;

			  	    int pivot = median(arr[low], arr[(low+high)/2], arr[high]);
			
			  	    while (i <= j) {
			  	      while (arr[i] < pivot) {
			  	        i++;
			  	      }
		
			  	      while (arr[j] > pivot) {
			  	        j--;
			  	      }
			
			  	      if (i < j) {
			  	        exchange(i, j);
			  	        i++;
			  	        j--;
			  	      } else if (i == j) { i++; j--; }
			  	    }
			
					  	    if (low < j)
					  	      quicksort4(low, j);
					  	    if (i < high)
					  	      quicksort4(i, high);
	  	    }
      }
         
    public static void main(String[] args) {
        
        read = new BufferedReader(new InputStreamReader(System.in));
        
        randomGenerator = new Random();
        
        try
        {
            System.out.print("Please enter array size : ");
            size = Integer.parseInt(read.readLine());
            
            System.out.print("Please enter the random range : ");
            random = Integer.parseInt(read.readLine());
            
            // create array
            arr = new int[size];
            arrCopy = new int[size];
            
            for(int k=1; k<=10; k++){
            // fill array
            for(int i=0; i<size; i++) {
                arr[i] = arrCopy[i] = randomGenerator.nextInt(random);
            }
            if (size < 101) { 
            	System.out.println("Initial array:");
            	printArray();
            }
            
            System.out.println("The No." + k + " instance");
            long start = System.currentTimeMillis();
            Arrays.sort(arr);
            if (size < 101) printArray();
            long finish = System.currentTimeMillis();
            System.out.println("Arrays.sort: " + (finish-start) + " milliseconds.");
            
            // Heap sort      
            start = finish;
            heapsort();
            if (size < 101) printArray();
            finish = System.currentTimeMillis();
            System.out.println("heapsort: " + (finish-start) + " milliseconds.");
 
            // Quick sort
            start = finish;
            for(int i=0; i<size; i++) arr[i] = arrCopy[i];
            quicksort(0, size-1);
            if (size < 101) printArray();
            finish = System.currentTimeMillis();
            System.out.println("quicksort: " + (finish-start) + " milliseconds.");
            
            // Merge sort, which destroys arrCopy[].
            start = finish;
            for(int i=0; i<size; i++) arr[i] = arrCopy[i];
            mergesort(0, size-1);
            if (size < 101) printArray();
            finish = System.currentTimeMillis();
            System.out.println("mergesort: " + (finish-start) + " milliseconds.");
            
            // Insert sort
            start = finish;
            for(int i=0; i<size; i++) arr[i] = arrCopy[i];
            insertSort(0, size-1);
            if (size < 101) printArray();
            finish = System.currentTimeMillis();
            System.out.println("insertsort: " + (finish-start) + " milliseconds.");
            
            // Quick sort1
            start = finish;
            for(int i=0; i<size; i++) arr[i] = arrCopy[i];
            quicksort1(0, size-1);
            if (size < 101) printArray();
            finish = System.currentTimeMillis();
            System.out.println("quicksort1: " + (finish-start) + " milliseconds.");
            
            // Quick sort2
            start = finish;
            for(int i=0; i<size; i++) arr[i] = arrCopy[i];
            quicksort2(0, size-1);
            if (size < 101) printArray();
            finish = System.currentTimeMillis();
            System.out.println("quicksort2: " + (finish-start) + " milliseconds.");
            
            // Quick sort3
            start = finish;
            for(int i=0; i<size; i++) arr[i] = arrCopy[i];
            quicksort3(0, size-1);
            if (size < 101) printArray();
            finish = System.currentTimeMillis();
            System.out.println("quicksort3: " + (finish-start) + " milliseconds.");
            
            // Quick sort4
            start = finish;
            for(int i=0; i<size; i++) arr[i] = arrCopy[i];
            quicksort4(0, size-1);
            if (size < 101) printArray();
            finish = System.currentTimeMillis();
            System.out.println("quicksort4: " + (finish-start) + " milliseconds.");
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
