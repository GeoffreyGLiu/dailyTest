package com.geoffrey.algorithm;

import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.geoffrey.algorithm.bean.CompareBean;

import org.joda.time.Interval;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.ToIntFunction;

/**
 * -------------------------------------
 * 作者：刘京帅 sumu
 * -------------------------------------
 * 时间：3/18/21
 * -------------------------------------
 * 描述：
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class AlgorithmUtils {

    /**
     * 计算一个int数组中,两个之和等于target的元素索引
     *
     * @param intArray
     * @param target
     * @return
     */
    public static int[] getTwoSum(int[] intArray, int target) {
        if (intArray == null || intArray.length <= 1) {
            return null;
        }
        int i = 0, j = intArray.length - 1;
        int sumTemp = 0;
        while (i < j) {
            sumTemp = intArray[i] + intArray[j];
            if (sumTemp == target) {
                return new int[]{i, j};
            } else if (sumTemp < target) {
                i++;
            } else {
                j--;
            }
        }
        return null;
    }

    /**
     * 计算一个int数,是否为两个数的平方和
     *
     * @param c
     * @return
     */
    public static boolean judgeSquareSum(int c) {
        if (c < 0) {
            return false;
        }
        int i = 0, j = (int) Math.sqrt(c);
        int squareSumTemp = 0;
        while (i <= j) {
            squareSumTemp = i * i + j * j;
            if (squareSumTemp == c) {
                return true;
            } else if (squareSumTemp < c) {
                i++;
            } else {
                j--;
            }
        }
        return false;
    }

    /**
     * 反转一个字符串中的元音字母
     * 例如: Given s = "leetcode", return "leotcede".
     *
     * @param str
     * @return
     */
    public static String reverseVowels(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        HashSet<Character> vowelsSet = new HashSet<>(Arrays.asList('a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U'));
        int i = 0, j = str.length() - 1;
        char[] charTemp = new char[str.length()];
        char charITemp, charJTemp;
        while (i < j) {
            charITemp = str.charAt(i);
            charJTemp = str.charAt(j);
            if (!vowelsSet.contains(charITemp)) {
                charTemp[i++] = charITemp;
            } else if (!vowelsSet.contains(charJTemp)) {
                charTemp[j++] = charJTemp;
            } else {
                charTemp[i++] = charJTemp;
                charTemp[j--] = charITemp;
            }
        }
        return new String(charTemp);
    }

    /**
     * 判断一个字符串是否是回文的或是删除一个字符的条件下,是否是回文的
     * 例如: 12321,回文
     * 例如: 123421,删除4,回文
     * 例如:12345664321,不回文
     *
     * @param str
     * @return
     */
    public static boolean validPalindrome(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        int i = -1, j = str.length();
        char iTemp, jTemp;
        while (++i < --j) {
            iTemp = str.charAt(i);
            jTemp = str.charAt(j);
            if (iTemp != jTemp) {
                return validPalindromeByDeleteOne(str, i + 1, j) || validPalindromeByDeleteOne(str, i, j - 1);
            }
        }
        return true;
    }

    public static boolean validPalindromeByDeleteOne(String str, int i, int j) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        char iTempDelete, jTempDelete;
        while (i++ < j--) {
            iTempDelete = str.charAt(i);
            jTempDelete = str.charAt(j);
            if (iTempDelete != jTempDelete) {
                return false;
            }
        }
        return true;
    }

    public static int[] mergeTwoOrderedArray(int[] arrayOne, int m, int[] arrayTwo, int n) {
        if (arrayTwo == null) {
            return arrayOne;
        }
        //倒序遍历,arrayOne中预留了空间,提供给arrayTwo并入
        //例如 arrayOne = [1,2,3,0,0,0],arrayTwo = [4,5,6]
        //倒序遍历,从arrayOne中预留的空间开始填充
        //m,n为两个Array的有效元素数量
        int i = m - 1, j = n - 1, mergeIndex = m + n - 1;
        while (i >= 0 || j >= 0) {
            if (i < 0) {
                arrayOne[mergeIndex--] = arrayTwo[j--];
            } else if (j < 0) {
                arrayOne[mergeIndex--] = arrayOne[i--];
            } else if (arrayOne[i] > arrayTwo[j]) {
                arrayOne[mergeIndex--] = arrayOne[i--];
            } else {
                arrayOne[mergeIndex--] = arrayTwo[j--];
            }
        }
        return arrayOne;
    }

    /**
     * 题目描述：删除 src 中的一些字符，使得它构成字符串列表 d 中的一个字符串，找出能构成的最长字符串
     * 如果有多个 相同长度的结果，返回字典序的最小字符串。
     * Input:src = "abpcplea", d = ["ale","apple","monkey","plea"]
     * Output: "apple"
     *
     * @param src
     * @param desList
     * @return
     */
    public static String findLongestWord(String src, List<String> desList) {
        String longestWordStr = "";
        int longestWordLength, desWordLength;
        for (String target : desList) {
            longestWordLength = longestWordStr.length();
            desWordLength = target.length();
            if (longestWordLength > desWordLength || ((longestWordLength == desWordLength) && longestWordStr.compareTo(target) < 0)) {
                continue;
            }
            if (isValid(src, target)) {
                longestWordStr = target;
            }
        }
        return longestWordStr;
    }

    private static boolean isValid(String src, String target) {
        if (src == null || target == null) {
            return false;
        }
        int i = 0, j = 0;
        while (i < src.length() && j < target.length()) {
            if (src.charAt(i) == target.charAt(j)) {
                j++;
            }
            i++;
        }
        return j == target.length();
    }

    /**
     * 找出array中第k打的值
     *
     * @param array
     * @param k
     * @return
     */
    public static int getKthLargest(int[] array, int k) {
        if (array == null || k > array.length) {
            return -1;
        }
        k = array.length - k;
        int left = 0, right = array.length - 1, j = 0;
        while (left < right) {
            j = partition(array, left, right);
            if (j == k) {
                break;
            } else if (j > k) {
                right = j - 1;
            } else {
                left = j + 1;
            }
        }
        return array[k];
    }

    private static int partition(int[] array, int left, int right) {
        int i = left, j = right + 1;
        while (true) {
            while (array[++i] < array[left] && i < right) ;
            while (array[--j] > array[left] && j > left) ;
            if (i >= j) {
                break;
            }
            swap(array, i, j);
        }
        swap(array, left, j);
        return j;
    }

    private static void swap(int[] array, int i, int j) {
        if (array == null || i >= array.length || j >= array.length) {
            return;
        }
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    /**
     * 给出一个集合,找出出现频率最高的k个数
     * array = [1,1,1,2,2,3] k = 2
     * result = {1,2}
     *
     * @param array
     * @param k
     * @return
     */
    public static List<Integer> findKthFrequence(int[] array, int k) {
        if (array == null || array.length < k) {
            return null;
        }
        Map<Integer, Integer> integerFrequencyMap = new HashMap<>();
        int value;
        for (int i : array) {
            value = !integerFrequencyMap.containsKey(i) ? 0 : integerFrequencyMap.get(i);
            integerFrequencyMap.put(i, value + 1);
        }
        List<Integer>[] listArray = new ArrayList[array.length + 1];
        for (int key : integerFrequencyMap.keySet()) {
            int frequency = integerFrequencyMap.get(key);
            if (listArray[frequency] == null) {
                listArray[frequency] = new ArrayList<>();
            }
            listArray[frequency].add(key);
        }
        List<Integer> topKList = new ArrayList<>();
        List<Integer> tempList;
        for (int i = listArray.length - 1; i > 0 && k > topKList.size(); i--) {
            tempList = listArray[i];
            if (tempList.size() <= (k - topKList.size())) {
                topKList.addAll(tempList);
            } else {
                //不懂唉,为何会有这一段
                topKList.addAll(listArray[i].subList(0, k - topKList.size()));
            }
        }
        return topKList;
    }

    /**
     * 荷兰国旗问题
     * 荷兰国旗包含三种颜色：红、白、蓝。 有三种颜色的球
     * 算法的目标是将这三种球按颜色顺序正确地排列。
     * 它其实是三向切分快速排序的一种变种，在三向切分快速排序中，每次切分都将数组分成三个区间：小于切分元素、 等于切分元素、大于切分元素
     * 而该算法是将数组分成三个区间：等于红色、等于白色、等于蓝色。
     *
     * @param nums
     */
    public static void sortColors(int[] nums) {
        if (nums == null || nums.length <= 0) {
            return;
        }
        int zero = -1, one = 0, two = nums.length;
        while (one < two) {
            if (nums[one] == 0) {
                swap(nums, ++zero, one++);
            } else if (nums[one] == 2) {
                swap(nums, --two, one);
            } else {
                one++;
            }
        }
    }

    /**
     * ：计算让一组区间不重叠所需要移除的区间个数。
     * 先计算最多能组成的不重叠区间个数，然后用区间总个数减去不重叠区间的个数
     * 在每次选择中，区间的结尾最为重要，选择的区间结尾越小，留给后面的区间的空间越大，那么后面能够选择的区间 个数也就越大。
     * 按区间的结尾进行排序，每次选择结尾最小，并且和前一个区间不重叠的区间。
     *
     * @param intervals
     * @return
     */
    public static int eraseOverlapInterval(Interval[] intervals) {
        if (intervals == null || intervals.length <= 0) {
            return -1;
        }
        Arrays.sort(intervals, new Comparator<Interval>() {
            @Override
            public int compare(Interval o1, Interval o2) {
                return (int) (o1.getEndMillis() - o2.getEndMillis());
            }
        });
        int cnt = 0;
        long end = intervals[0].getEndMillis();
        long startTemp;
        for (Interval interval : intervals) {
            startTemp = interval.getStartMillis();
            if (startTemp < end) {
                continue;
            }
            cnt++;
        }
        return intervals.length - cnt;
    }

    /**
     * just for test,比较器Comparator测试使用
     * https://blog.csdn.net/wlh2015/article/details/83959462
     *
     * @return
     */
    public static void testComparator() {
        CompareBean[] interval = {new CompareBean(3), new CompareBean(4), new CompareBean(1), new CompareBean(2),};
        Arrays.sort(interval, new Comparator<CompareBean>() {
            @Override
            public int compare(CompareBean o1, CompareBean o2) {
                return o1.getValue() - o2.getValue();
            }
        });
        Log.e("test", "array = " + arrayBeanToString(interval));
    }

    /**
     * 投飞镖刺破气球
     * Input:[[10,16], [2,8], [1,6], [7,12]]
     * Output: 2
     * <p>
     * 题目描述：气球在一个水平数轴上摆放，可以重叠，飞镖垂直投向坐标轴，使得路径上的气球都会刺破。求解最小的 投飞镖次数使所有气球都被刺破。
     * 也是计算不重叠的区间个数，不过和 Non-overlapping Intervals 的区别在于，[1, 2] 和 [2, 3] 在本题中算是重叠区间。
     *
     * @param points
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static int findMinArrowShots(final int[][] points) {
        if (points == null || points.length <= 0) {
            return 0;
        }
//        Arrays.sort(points, (o1, o2) -> o1[1] - o2[1]);
        Arrays.sort(points, Comparator.comparingInt(o -> o[1]));
        int cnt = 0, end = 0;
        int startTemp;
        for (int[] intArray : points) {
            startTemp = intArray[0];
            if (startTemp <= end) {
                continue;
            }
            cnt++;
            end = intArray[1];
        }
        return cnt;
    }


    private static String arrayBeanToString(CompareBean[] array) {
        if (array == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (CompareBean i : array) {
            builder.append(i.getValue());
            builder.append(",");
        }
        return builder.toString();
    }

    /**
     * 根据身高和序号重组队列
     * Input:[[7,0], [4,4], [7,1], [5,0], [6,1], [5,2]]
     * Output:[[5,0], [7,0], [5,2], [6,1], [4,4], [7,1]]
     * <p>
     * 题目描述：一个学生用两个分量 (h, k) 描述，h 表示身高，k 表示排在前面的有 k 个学生的身高比他高或者和他一样 高。
     * 为了使插入操作不影响后续的操作，身高较高的学生应该先做插入操作，否则身高较小的学生原先正确插入的第 k 个 位置可能会变成第 k+1 个位置。
     * 身高降序、k 值升序，然后按排好序的顺序插入队列的第 k 个位置中。
     *
     * @param originQueue
     * @return
     */
    private static int[][] reConstructQueue(int[][] originQueue) {
        if (originQueue == null || originQueue.length == 0 || originQueue[0].length == 0) {
            return new int[0][0];
        }
        Arrays.sort(originQueue, ((o1, o2) -> o1[0] == o2[0] ? o1[1] - o2[1] : o2[0] - o1[0]));
        List<int[]> newList = new ArrayList<>();
        for (int[] p : originQueue) {
            newList.add(p[1], p);
        }
        return newList.toArray(new int[newList.size()][]);
    }


    /**
     * 分隔字符串使同种字符出现在一起
     * Input: S = "ababcbacadefegdehijhklij"
     * Output: [9,7,8]
     * Explanation: "ababcbaca", "defegde", "hijhklij".
     *
     * @param S
     * @return
     */
    public static List<Integer> partitionLabels(String S) {

        int[] lastIndexsOfChar = new int[26];
        for (int i = 0; i < S.length(); i++) {
            lastIndexsOfChar[char2Index(S.charAt(i))] = i;
        }
        List<Integer> partitions = new ArrayList<>();
        int firstIndex = 0;
        while (firstIndex < S.length()) {
            int lastIndex = firstIndex;
            for (int i = firstIndex; i < S.length() && i <= lastIndex; i++) {
                int index = lastIndexsOfChar[char2Index(S.charAt(i))];
                if (index > lastIndex) {
                    lastIndex = index;
                }
            }
            partitions.add(lastIndex - firstIndex + 1);
            firstIndex = lastIndex + 1;
        }
        return partitions;
    }

    private static int char2Index(char c) {
        return c - 'a';
    }

    /**
     * 题目描述：花朵之间至少需要一个单位的间隔，求解是否能种下 n 朵花。
     * Input: flowerbed = [1,0,0,0,1], n = 1
     * Output: True
     *
     * @param originArray
     * @param n
     * @return
     */
    public static boolean isCanPlantFlowers(int[] originArray, int n) {
        if (originArray == null || originArray.length <= 2 || n <= 0) {
            return false;
        }
        int length = originArray.length;
        int count = 0;
        int pre, next;
        for (int i = 0; i < length && count < n; i++) {
            if (originArray[i] == 1) {
                continue;
            }
            pre = i == 0 ? 0 : originArray[i - 1];
            next = originArray[(i + 1) % length];
            if (pre == 0 && next == 0) {
                originArray[i] = 1;
                count++;
            }
        }
        return count >= n;
    }

    /**
     * 判断是否为子序列
     * s = "bca", t = "ahbgdc"
     *
     * @param str
     * @param subString
     * @return
     */
    public static boolean isSubsequence(String str, String subString) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(subString) || str.length() < subString.length()) {
            return false;
        }
        int index = -1;
        for (char c : subString.toCharArray()) {
            //这里使用
            //index = str.indexOf(c,index + 1);
            //提高检索效率,但是上述序列为"bca"的子序列,会返回false
            //如果子序列为"abc",则为true.
            index = str.indexOf(c);
            if (index == -1) {
                return false;
            }
        }
        return true;
    }

    /**
     * 题目描述：判断一个数组能不能只修改一个数就成为非递减数组。
     * Input: [4,2,3] Output: True
     * 非递减数列的： 对于数组中所有的 i (1 <= i < n)，满足 array[i] <= array[i + 1]。
     *
     * @param array
     * @return
     */
    public static boolean checkPosibility(int[] array) {
        if (array == null || array.length <= 0) {
            return false;
        }
        int count = 0;
        for (int i = 1; i < array.length && count < 2; i++) {
            if (array[i - 1] <= array[i]) {
                continue;
            }
            count++;
            if (i - 2 > 0 && array[i - 2] > array[i]) {
                array[i] = array[i - 1];
            } else {
                array[i - 1] = array[i];
            }
        }
        return count < 2;
    }

    /**
     * 题目描述：一次股票交易包含买入和卖出，多个交易之间不能交叉进行。
     * <p>
     * 对于 [a, b, c, d]，如果有 a <= b <= c <= d ，那么最大收益为 d - a。
     * 而 d - a = (d - c) + (c - b) + (b - a) ，
     * 因此当访问到一 个 prices[i] 且 prices[i] - prices[i-1] > 0，那么就把 prices[i] - prices[i-1] 添加到收益中，
     * 从而在局部最优的情况下也保证 全局最优。
     *
     * @param array
     * @return
     */
    public static int maxprofit(int[] array) {
        if (array == null || array.length <= 0) {
            return 0;
        }
        int maxProfit = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i - 1] < array[i]) {
                maxProfit += (array[i] - array[i - 1]);
            }
        }
        return maxProfit;
    }

    /**
     * 子数组最大的和
     * given the array [-2,1,-3,4,-1,2,1,-5,4],
     * the contiguous subarray [4,-1,2,1] has the largest sum = 6.
     *
     * @param array
     * @return
     */
    public static int maxArraySum(int[] array) {
        if (array == null || array.length <= 0) {
            return 0;
        }
        int maxArraySum = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] > 0) {
                maxArraySum += array[i];
            }
        }
        return maxArraySum;
    }

    /**
     * 二分查找
     * @param array
     * @param key
     * @return
     */
    public static int binarySearch(int[] array, int key) {
        if (array == null || array.length <= 0) {
            return 0;

        }
        int l = 0, h = array.length - 1;
        int m = 0;
        while (l <= h) {
            m = l + (h - 1) / 2;
            if (array[m] == key) {
                return m;

            } else if (array[m] > key) {
                h = m - 1;
            } else {
                l = m + 1;
            }
        }
        return -1;
    }

    private static String arrayToString(int[] array) {
        if (array == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (int i : array) {
            builder.append(i);
            builder.append(",");
        }
        return builder.toString();
    }
}
