import java.util.Arrays;

public class Main {

    public static char numberToLetter(int num) {
        char letter = 'x';
        switch (num) {
            case 0:
                letter = 'а';
                break;
            case 1:
                letter = 'б';
                break;
            case 2:
                letter = 'в';
                break;
            case 3:
                letter = 'г';
                break;
            case 4:
                letter = 'д';
                break;
            case 5:
                letter = 'е';
                break;
            case 6:
                letter = 'ж';
                break;
            case 7:
                letter = 'з';
                break;
        }
        return letter;
    }

    public static int binaryToInteger(String binary) {
        char[] numbers = binary.toCharArray();
        int result = 0;
        for (int i = numbers.length - 1; i >= 0; i--)
            if (numbers[i] == '1')
                result += Math.pow(2, (numbers.length - i - 1));
        return result;
    }

    public static String arrayToBinaryString(int arr[], int length) {
        int sum = 0;
        for (int i = 0; i < arr.length; i++)
            sum += arr[i] * Math.pow(10, arr.length - i - 1);
        String bin = Integer.toBinaryString(sum);
        while (bin.length() < length)
            bin = "0" + bin;
        return bin;
    }

    public static String eachDigitToBinaryString(int arr[]) {
        String bin = "", temp;
        for (int i = 0; i < arr.length; i++) {
            temp = Integer.toBinaryString(arr[i]);
            while (temp.length() < 3)
                temp = "0" + temp;
            bin += temp + " ";
        }
        return bin;
    }

    public static String xorBinaryString(String first, String second) {
        String[] firstArr = first.split(" ");
        String[] secondArr = second.split(" ");
        String result = "", temp;
        for (int i = 0; i < firstArr.length; i++) {
            temp = Integer.toBinaryString(binaryToInteger(firstArr[i]) ^ binaryToInteger(secondArr[i]));
            while (temp.length() < 3)
                temp = "0" + temp;
            result += temp + " ";
        }
        return result;
    }

    public static void main(String[] args) {
        /*int[] key = {2, 9, 2, 4};
        int[] ciphertext = {7, 1, 4, 5, 6, 7, 5};*/
        /*int[] key = {1, 7, 6, 9};
        int[] ciphertext = {1, 1, 5, 2, 4, 0, 7};*/
        int[] key = {1, 0, 0, 7};
        int[] ciphertext = {3, 5, 5, 1, 2, 5, 6};

        int j = 0, jOld, i, iOld, temp, l = key.length;
        String bin = arrayToBinaryString(key, 12);
        System.out.println("Двоичный код ключа шифрования: " + bin);

        temp = bin.length() / 4;
        for (i = 1; i <= l; i++)
            key[l - i] = binaryToInteger(bin.substring((i - 1) * temp, i * temp));
        System.out.println("\"Перевёрнутый\" двоичный код ключа шифрования: " + Arrays.toString(key));

        int[] s = new int[8];
        for (i = 0; i < s.length; i++) s[i] = i;
        System.out.println("Начальное заполнение S - блока:\n 0  1  2  3  4  5  6  7  | i  j = (j+s[i]+key[i%l])%8");
        for (i = 0; i < s.length; i++) {
            jOld = j;
            j = (j + s[i] + key[i % l]) % 8;
            temp = s[i];
            s[i] = s[j];
            s[j] = temp;
            System.out.printf("%s | %d  %d = (%d+%d+%d)%%8%n", Arrays.toString(s), i, j, jOld, temp, key[i % l]);
        }
        System.out.println("S = " + Arrays.toString(s) + "\n");

        i = j = 0;
        int k[] = new int[7];
        System.out.println("Генерация псевдослучайного слова K:\nn | i = (i+1)%8 | j=(j+s[i])%8|  0  1  2  3  4  5  6  7  | k[n]=s[(s[i]+s[j])%8]");
        for (int n = 0; n < k.length; n++) {
            iOld = i;
            i = (i + 1) % 8;
            jOld = j;
            j = (j + s[i]) % 8;
            temp = s[i];
            s[i] = s[j];
            s[j] = temp;
            k[n] = s[(s[i] + s[j]) % 8];
            System.out.printf("%d | %d = (%d+1)%%8 | %d = (%d+%d)%%8 | %s | %d = s[(%d+%d)%%8]%n", n, i, iOld, j, jOld, temp, Arrays.toString(s), k[n], s[i], s[j]);
        }
        System.out.println("Потоковый ключ: " + Arrays.toString(k));

        System.out.println("\nk          = " + eachDigitToBinaryString(k));
        System.out.println("ciphertext = " + eachDigitToBinaryString(ciphertext));
        System.out.println("xor        = " + xorBinaryString(eachDigitToBinaryString(k), eachDigitToBinaryString(ciphertext)) + "=>");

        System.out.print("Открытый текст: ");
        for (i = 0; i < ciphertext.length; i++)
            System.out.print((k[i] ^ ciphertext[i]) + " ");
        System.out.print("=> ");
        for (i = 0; i < ciphertext.length; i++)
            System.out.print(numberToLetter(k[i] ^ ciphertext[i]) + " ");
    }
}