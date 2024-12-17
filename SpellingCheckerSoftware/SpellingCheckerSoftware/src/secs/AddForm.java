package secs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Set;

public class AddForm extends JFrame {

    // Khai báo kích thước chiều rộng và chiều cao của cửa sổ
    private final int WIDTH = 500;
    private final int HEIGHT = 150;

    // Khai báo văn bản placeholder
    private final String PLACE_HOLDER_TEXT = "Nhập cụm từ cần thêm tại đây...";

    // Khai báo đường dẫn tệp chứa từ điển
    private final String FILE_PATH = "vietnamese_words.txt";

    // Khai báo các thành phần giao diện
    private JPanel mainPanel;
    private JLabel captionLabel;
    private JLabel wordCountLabel;
    private JTextField newWordTextField;
    private JButton addButton;

    // Tập hợp các từ trong từ điển
    private Set<String> wordsDictionary;

    // Quản lý từ điển
    private DictionaryManager dictionaryManager;

    // Phương thức khởi tạo form thêm từ, truyền vào label đếm từ
    public AddForm(JLabel wordsCount) {
        this.dictionaryManager = new DictionaryManager(FILE_PATH); // Tạo đối tượng quản lý từ điển
        this.wordsDictionary = dictionaryManager.getDictionary();  // Lấy từ điển
        this.wordCountLabel = wordsCount; // Gán nhãn đếm từ được truyền vào
        initComponents(); // Khởi tạo các thành phần giao diện
    }

    // Phương thức khởi tạo các thành phần giao diện
    public void initComponents() {
        mainPanel = new JPanel(); // Tạo panel chính
        mainPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));  // Thiết lập layout cho panel
        mainPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  // Đặt kích thước ưa thích cho panel
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10)); // Đặt khoảng trống cho panel

        captionLabel = new JLabel("Nội dung cần thêm:"); // Nhãn chỉ dẫn cho người dùng
        mainPanel.add(captionLabel); // Thêm nhãn vào panel

        // Tạo trường văn bản để nhập từ mới
        newWordTextField = new JTextField(40);
        newWordTextField.setText(PLACE_HOLDER_TEXT); // Đặt văn bản placeholder ban đầu
        newWordTextField.setForeground(Color.GRAY); // Đặt màu xám cho placeholder

        // Thêm sự kiện khi người dùng click vào trường văn bản
        newWordTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                // Khi người dùng focus vào trường văn bản, xóa placeholder và đổi màu chữ thành đen
                if (newWordTextField.getText().equals(PLACE_HOLDER_TEXT)) {
                    newWordTextField.setText("");
                    newWordTextField.setForeground(Color.BLACK);
                } else {
                    newWordTextField.selectAll(); // Chọn toàn bộ văn bản nếu không phải là placeholder
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                // Nếu người dùng không nhập gì và rời khỏi trường văn bản, khôi phục lại placeholder
                if (newWordTextField.getText().isEmpty()) {
                    newWordTextField.setText(PLACE_HOLDER_TEXT);
                    newWordTextField.setForeground(Color.GRAY);
                }
            }
        });

        mainPanel.add(newWordTextField); // Thêm trường văn bản vào panel

        // Tạo nút "Thêm" và thêm sự kiện khi nhấn nút
        addButton = new JButton("Thêm");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lấy từ người dùng nhập
                String newWord = newWordTextField.getText();
                try {
                    // Kiểm tra nếu từ chưa có trong từ điển
                    if (!wordsDictionary.contains(newWord)) {
                        dictionaryManager.addWord(newWord); // Thêm từ vào từ điển
                        JOptionPane.showMessageDialog(AddForm.this, String.format("Từ \"%s\" đã được thêm vào từ điển", newWord));
                        wordsDictionary = dictionaryManager.getDictionary(); // Cập nhật lại từ điển
                        wordCountLabel.setText(String.format("Từ điển có %d từ vựng", wordsDictionary.size())); // Cập nhật nhãn đếm từ
                    } else {
                        // Hiển thị thông báo nếu từ đã có trong từ điển
                        JOptionPane.showMessageDialog(AddForm.this, String.format("Từ \"%s\" đã có trong từ điển", newWord));
                    }
                } catch (Exception ex) {
                    // Hiển thị thông báo lỗi nếu có ngoại lệ
                    JOptionPane.showMessageDialog(AddForm.this, ex.getMessage());
                }

                // Đặt focus lại vào trường văn bản để tiếp tục nhập
                newWordTextField.requestFocusInWindow();
            }
        });

        mainPanel.add(addButton); // Thêm nút "Thêm" vào panel

        add(mainPanel); // Thêm panel chính vào frame

        // Thiết lập các thuộc tính cho cửa sổ JFrame
        setSize(new Dimension(WIDTH, HEIGHT));
        setTitle("Thêm từ mới");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Cửa sổ xuất hiện ở giữa màn hình
        setResizable(false); // Không cho phép thay đổi kích thước cửa sổ
        setVisible(true); // Hiển thị cửa sổ
    }
}