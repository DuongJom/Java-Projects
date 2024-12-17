package secs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Set;

public class SearchForm extends JFrame {

    private final int WIDTH = 500;
    private final int HEIGHT = 400;
    private final String PLACE_HOLDER_TEXT = "Nhập nội dung tra cứu tại đây...";
    private final String FILE_PATH = "vietnamese_words.txt";

    private JPanel mainPanel;
    private JLabel captionLabel;
    private JLabel wordCountLabel;
    private JTextField searchTextField;
    private JButton searchButton;
    private JTextPane resultTextPane;

    private Set<String> wordsDictionary;
    private DictionaryManager dictionaryManager;
    private String formatWordCount = "Từ điển có %d từ vựng";

    // Khởi tạo SearchForm với một DictionaryManager để quản lý từ điển
    public SearchForm(JLabel wordCount) {
        this.dictionaryManager = new DictionaryManager(FILE_PATH);
        this.wordsDictionary = dictionaryManager.getDictionary(); // Lấy từ điển từ tệp
        this.wordCountLabel = wordCount;
        initComponents(); // Khởi tạo các thành phần giao diện người dùng
    }

    // Phương thức khởi tạo giao diện người dùng
    public void initComponents() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Sắp xếp các thành phần trong panel
        mainPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT)); // Kích thước của panel
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10)); // Thêm lề cho panel

        // Tạo label mô tả
        captionLabel = new JLabel("Nội dung cần tra cứu:");
        mainPanel.add(captionLabel); // Thêm label vào panel

        // Tạo trường nhập liệu cho người dùng
        searchTextField = new JTextField(40);
        searchTextField.setText(PLACE_HOLDER_TEXT); // Đặt placeholder cho trường nhập liệu
        searchTextField.setForeground(Color.GRAY); // Đặt màu chữ cho placeholder
        // Lắng nghe sự kiện focus (nhấp chuột vào trường nhập liệu)
        searchTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                // Nếu người dùng click vào trường nhập liệu, xóa placeholder
                if (searchTextField.getText().equals(PLACE_HOLDER_TEXT)) {
                    searchTextField.setText(""); 
                    searchTextField.setForeground(Color.BLACK); // Đổi màu chữ về đen
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                // Nếu người dùng không nhập gì, trả lại placeholder
                if (searchTextField.getText().isEmpty()) {
                    searchTextField.setText(PLACE_HOLDER_TEXT);
                    searchTextField.setForeground(Color.GRAY); // Đặt lại màu chữ cho placeholder
                }
            }
        });
        mainPanel.add(searchTextField); // Thêm trường nhập liệu vào panel

        // Tạo nút "Tra cứu"
        searchButton = new JButton("Tra cứu");
        // Lắng nghe sự kiện click vào nút "Tra cứu"
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resultTextPane.setText(""); // Xóa nội dung cũ trong kết quả
                String searchString = searchTextField.getText(); // Lấy nội dung người dùng nhập
                String result = ""; // Biến chứa kết quả tìm kiếm
                // Duyệt qua các từ trong từ điển và tìm những từ bắt đầu bằng chuỗi người dùng nhập
                for (String word : wordsDictionary) {
                    if (word.startsWith(searchString)) {
                        result += word + "\n"; // Thêm từ tìm được vào kết quả
                    }
                }
                if(result.length() > 0){
                    resultTextPane.setText(result); // Hiển thị kết quả
                }
                else{
                    //JOptionPane.showMessageDialog(SearchForm.this, "Không tìm thấy!"); // Thông báo nếu không tìm thấy kết quả
                    // Hiển thị hộp thoại xác nhận thêm từ vào từ điển
                            int response = JOptionPane.showConfirmDialog(
                                SearchForm.this, 
                                String.format("Từ \"%s\" chưa có trong từ điển. Bạn có muốn thêm từ này vào không?", searchString), 
                                "Xác nhận", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE
                            );

                            // Nếu người dùng đồng ý thêm từ vào từ điển
                            if(response == JOptionPane.YES_OPTION){
                                try{
                                    dictionaryManager.addWord(searchString); // Thêm từ vào từ điển
                                    JOptionPane.showMessageDialog(SearchForm.this, String.format("Từ \"%s\" đã được thêm vào từ điển", searchString));

                                    // Cập nhật lại từ điển và đếm số từ
                                    wordsDictionary = dictionaryManager.getDictionary();
                                    wordCountLabel.setText(String.format(formatWordCount, wordsDictionary.size()));
                                }
                                catch(Exception ex){}
                            }
                }
            }
        });
        mainPanel.add(searchButton); // Thêm nút vào panel

        // Tạo một JTextPane để hiển thị kết quả tìm kiếm
        resultTextPane = new JTextPane();
        resultTextPane.setEditable(false); // Không cho phép chỉnh sửa nội dung kết quả
        resultTextPane.setPreferredSize(new Dimension(WIDTH - 60, HEIGHT - 170)); // Kích thước của kết quả
        mainPanel.add(resultTextPane); // Thêm JTextPane vào panel

        // Thêm panel vào JFrame
        add(mainPanel);

        // Thiết lập các thuộc tính của JFrame
        setSize(new Dimension(WIDTH, HEIGHT)); // Kích thước cửa sổ
        setTitle("Tra cứu"); // Tiêu đề cửa sổ
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Đóng cửa sổ khi nhấn nút đóng
        setLocationRelativeTo(null); // Đặt cửa sổ ở giữa màn hình
        setResizable(false); // Không cho phép thay đổi kích thước cửa sổ
        setVisible(true); // Hiển thị cửa sổ
    }
}