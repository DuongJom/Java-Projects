package secs;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class DictionaryManager {

    // Khai báo một tập hợp chứa các từ trong từ điển
    private Set<String> wordDictionary;

    // Đường dẫn tới tệp từ điển
    private String dictionaryFilePath;

    // Phương thức khởi tạo, truyền vào đường dẫn của tệp từ điển
    public DictionaryManager(String dictionaryFilePath) {
        // Khởi tạo một HashSet để chứa các từ trong từ điển
        this.wordDictionary = new HashSet<String>();
        // Gán đường dẫn tệp từ điển
        this.dictionaryFilePath = dictionaryFilePath;
    }

    // Phương thức trả về từ điển dưới dạng Set
    public Set<String> getDictionary() {
        // Đọc tệp từ điển với mã hóa UTF-8
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(dictionaryFilePath), StandardCharsets.UTF_8))) {
            String line;
            // Đọc từng dòng từ tệp và thêm vào Set từ điển
            while ((line = reader.readLine()) != null) {
                wordDictionary.add(line); 
            }
        } catch (FileNotFoundException e) {
            // Xử lý trường hợp tệp không tìm thấy
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            // Xử lý các lỗi I/O
            System.out.println("IOException: " + e.getMessage());
        }

        // Trả về Set chứa từ điển
        return wordDictionary;
    }

    // Phương thức thêm từ mới vào tệp từ điển
    public void addWord(String word) throws IOException {
        // Mở tệp từ điển và thêm từ vào cuối tệp
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dictionaryFilePath, true), StandardCharsets.UTF_8))) {
            writer.write("\n" + word); // Thêm từ mới vào tệp
            writer.flush(); // Đảm bảo dữ liệu được ghi vào tệp ngay lập tức
            writer.close(); // Đóng tệp sau khi ghi
        } catch (IOException e) {
            // Xử lý lỗi khi ghi vào tệp
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }
    
    // Phương thức tìm kiếm các từ bắt đầu bằng một tiền tố nhất định
    public Set<String> getWordsLike(String wordPrefix) {
        // Khởi tạo tập hợp để chứa các từ tìm được
        Set<String> words = new HashSet<String>();
        // Lặp qua các từ trong từ điển
        for (String word : wordDictionary) {
            // Kiểm tra nếu từ bắt đầu bằng tiền tố được cung cấp
            if (word.startsWith(wordPrefix)) {
                words.add(word); // Thêm từ vào tập hợp kết quả
            }
        }
        return words; // Trả về tập hợp các từ tìm được
    }
}