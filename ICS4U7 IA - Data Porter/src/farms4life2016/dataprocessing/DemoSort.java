import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class DemoSort {
	
	public static void main(String args[]) {
		
		int[] mess = {4, 9 ,2, 56, 2, 1, 38, 8, 2};
		
		mergesort(mess, 0, mess.length-1);
		
		System.out.println(Arrays.toString(mess));
		
	}
	
	public static void mergesort(int[] a) {
		mergesort(a, 0, a.length-1);
	}
	
	public static void mergesort(int[] a, int l, int r) {
		
		if (l < r) {
			
			int mid = (l + r)/2;
			
			mergesort(a, l, mid);
			mergesort(a, mid+1, r);
			
			merge(a, l, mid, r);
			
		} else {
			
		}
		
	}
	
	public static void merge(int[] a, int l, int mid, int r) {
		
		int leftPointer = l, rightPointer = mid + 1;
		int[] temp = new int[r-l+1];
		
		for (int i = 0; i < temp.length; i++) {
			
			if (leftPointer > mid) {
				temp[i] = a[rightPointer];
				rightPointer++;
			} else if (rightPointer > r) {
				temp[i] = a[leftPointer];
				leftPointer++;
			}
			
			else if (a[leftPointer] <= a[rightPointer]) {
				temp[i] = a[leftPointer];
				leftPointer++;
			} else {
				temp[i] = a[rightPointer];
				rightPointer++;
			}
			
		}
		
		for (int i = 0; i < temp.length; i++) {
			a[l+i] = temp[i];
		}
		
		
		
	}
	
	
	
	/*
	 * Simple Sorting:
	 * Insertion
	 * Selection
	 * Bubble
	 */

	//stable sorts don't modify elements that are already sorted
	
	/**
	 * Insertion sort
	 * 
	 * Reads an unsorted array from left to right. The first digit that it reads
	 * goes into another "sorted" array. The next digit gets "inserted" in
	 * the "sorted" array in the correct place. This process continues the unsorted
	 * array is empty and the "sorted" array is full.
	 * 
	 * Note: when the insertion occurs, elements to the right of the inserted
	 * element have their index incremented by 1 (shifts to the right by 1).
	 * 
	 * Good for almost sorted lists. Terrible at completely random lists.
	 * Best: O(n)
	 * Worst: O(n^2)
	 */
	
	
	/**
	 * Selection sort
	 * 
	 * Loop through the array and find the smallest/greatest element. Swap that 
	 * element with the first element. Then search the array from the 
	 * second-smallest/largest element and swap it with the second element. 
	 * This process repeats until len-1 swaps
	 * 
	 * Always O(n^2) so this sort is sh*
	 */
	static List<Integer> selectionSort(List<Integer> unsorted) {
		List<Integer> sorted = new LinkedList<>(unsorted);
		Collections.sort(sorted);
		return sorted;
		
	}
	
	/**
	 * Bubble sort
	 * 
	 * loop through the array and compare each adjacent pair. swap if necessary.
	 * by the end of the iteration, the largest/smallest element should be
	 * at the end of the array. Stop after a pass does not swap anything or after 
	 * pass # = len of array
	 * 
	 * One iteration = one "pass"
	 * 
	 * Best case: n-1 
	 * Worse case: O(n^2)
	 */
	
	
	/**
	 * generate an array with 10 elements. each element has a random number
	 * from 0 to 9
	 * @return
	 */
	static List<Integer> rng() {
		
		List<Integer> x = new LinkedList<>();
		
		for (int i = 0; i < 10; i++) {
			x.add( (int)(Math.random()*10));
		}
		
		System.out.println("Unsorted:\t" + x);
		
		return x;
		
	}
	
	//binary search efficiency: log2(n)
	
}
