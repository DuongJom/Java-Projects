package secs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

public class WordListForm extends JFrame {

    private final int WIDTH = 600;
    private final int HEIGHT = 600;

    private JPanel mainPanel;
    private JTextPane resultTextPane;

    private Set<String> wordsDictionary;

    // Khởi tạo SearchForm với một DictionaryManager để quản lý từ điển
    public WordListForm(Set<String> dictionary) {
        this.wordsDictionary = dictionary;
        initComponents(); // Khởi tạo các thành phần giao diện người dùng
    }

    // Phương thức khởi tạo giao diện người dùng
    public void initComponents() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Sắp xếp các thành phần trong panel
        mainPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT)); // Kích thước của panel
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10)); // Thêm lề cho panel

        // Tạo một JTextPane để hiển thị kết quả tìm kiếm
        resultTextPane = new JTextPane();
        resultTextPane.setEditable(false); // Không cho phép chỉnh sửa nội dung kết quả

        String result = "";
        for (String word : wordsDictionary) {
            result += word + "\n";
        }
        resultTextPane.setText(result);

        // Create a JScrollPane and add the resultTextPane to it
        JScrollPane scrollPane = new JScrollPane(resultTextPane);
        scrollPane.setPreferredSize(new Dimension(WIDTH - 55, HEIGHT - 70)); // Kích thước của JScrollPane
        mainPanel.add(scrollPane); // Add JScrollPane to the panel

        // Thêm panel vào JFrame
        add(mainPanel);

        // Thiết lập các thuộc tính của JFrame
        setSize(new Dimension(WIDTH, HEIGHT)); // Kích thước cửa sổ
        setTitle("Danh sách từ vựng"); // Tiêu đề cửa sổ
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Đóng cửa sổ khi nhấn nút đóng
        setLocationRelativeTo(null); // Đặt cửa sổ ở giữa màn hình
        setResizable(false); // Không cho phép thay đổi kích thước cửa sổ
        setVisible(true); // Hiển thị cửa sổ
    }
}
