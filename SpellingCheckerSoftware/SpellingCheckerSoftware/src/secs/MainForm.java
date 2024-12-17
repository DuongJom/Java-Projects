// Khai báo package "secs"
package secs;

// Import các thư viện cần thiết
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Set;

// Khai báo lớp MainForm, kế thừa từ JFrame
public class MainForm extends JFrame {

    // Khai báo các hằng số cho kích thước và giá trị khác nhau
    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    private final int LEFT_PANEL_WIDTH = 200;
    private final int BUTTON_WIDTH = 150;
    private final int BUTTON_HEIGHT = 30;
    private final String PLACE_HOLDER_TEXT = "Nhập đoạn văn tại đây....";
    private final String FILE_PATH = "vietnamese_words.txt";
    private final String REGEX_SPLIT_TEXT = "[,.:; ]"; // Biểu thức chính quy để tách văn bản

    // Khai báo các thành phần giao diện
    private JPanel mainPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JLabel logoLabel;
    private JLabel wordsCountLabel;
    private ImageIcon logoIcon;
    private JButton checkSpellingButton;
    private JButton searchButton;
    private JButton addButton;
    private JButton listButton;
    private JButton introduceButton;
    private JButton exitButton;
    private JTextPane inputTextPane;
    private JScrollPane textPaneScrollPane;

    // Khai báo các biến quản lý từ điển
    private DictionaryManager dictionaryManager;
    private Set<String> wordsDictionary;
    private String formatWordCount = "Từ điển có %d từ vựng";

    // Phương thức khởi tạo MainForm
    public MainForm() {
        // Khởi tạo đối tượng DictionaryManager và tải từ điển
        dictionaryManager = new DictionaryManager(FILE_PATH);
        wordsDictionary = dictionaryManager.getDictionary();
        initComponents(); // Khởi tạo các thành phần giao diện
    }

    // Phương thức khởi tạo các thành phần giao diện
    private void initComponents() {
        // Tạo panel chính với layout BorderLayout
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Tạo panel bên trái với kích thước cố định và nền trắng
        leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(LEFT_PANEL_WIDTH, HEIGHT)); 
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));  

        // Tải và chỉnh sửa logo cho phù hợp với kích thước panel
        logoIcon = new ImageIcon(getClass().getResource("/icons/logo.png"));
        Image image = logoIcon.getImage(); 
        Image scaledImage = image.getScaledInstance(LEFT_PANEL_WIDTH, LEFT_PANEL_WIDTH, Image.SCALE_SMOOTH);
        logoIcon = new ImageIcon(scaledImage);

        // Tạo nhãn logo và căn giữa nó
        logoLabel = new JLabel(logoIcon);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Tạo nhãn hiển thị số lượng từ trong từ điển và căn giữa
        wordsCountLabel = new JLabel(String.format(formatWordCount, wordsDictionary.size()));
        wordsCountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Tạo các nút chức năng với kích thước và căn giữa
        checkSpellingButton = new JButton("Kiểm tra chính tả");
        checkSpellingButton.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        checkSpellingButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        searchButton = new JButton("Tra từ");
        searchButton.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        searchButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        addButton = new JButton("Thêm từ mới");
        addButton.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        listButton = new JButton("Danh sách từ vựng");
        listButton.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        listButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        introduceButton = new JButton("Giới thiệu");
        introduceButton.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        introduceButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        exitButton = new JButton("Thoát");
        exitButton.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Thêm các thành phần vào panel bên trái
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(logoLabel);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(wordsCountLabel);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(checkSpellingButton);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(searchButton);
        leftPanel.add(Box.createVerticalStrut(10)); 
        leftPanel.add(addButton);
        leftPanel.add(Box.createVerticalStrut(10)); 
        leftPanel.add(listButton);
        leftPanel.add(Box.createVerticalStrut(90)); 
        leftPanel.add(introduceButton);
        leftPanel.add(Box.createVerticalStrut(10)); 
        leftPanel.add(exitButton);

        // Tạo panel bên phải để nhập văn bản
        rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setLayout(new BorderLayout()); 
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Tạo JTextPane để nhập văn bản với placeholder
        inputTextPane = new JTextPane();
        inputTextPane.setText(PLACE_HOLDER_TEXT);
        inputTextPane.setBackground(Color.WHITE);
        inputTextPane.setEditable(true);
        
        // Tạo style màu đỏ để hiển thị từ sai
        Style redStyle = inputTextPane.addStyle("Red", null);
        StyleConstants.setForeground(redStyle, Color.RED);

        // Tạo JScrollPane cho JTextPane
        textPaneScrollPane = new JScrollPane(inputTextPane);
        textPaneScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        textPaneScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); 

        // Xử lý sự kiện khi JTextPane lấy và mất tiêu điểm
        // Thêm FocusListener cho inputTextPane để xử lý sự kiện khi JTextPane lấy và mất tiêu điểm
        inputTextPane.addFocusListener(new FocusAdapter() {

            // Ghi đè phương thức focusGained để xử lý khi JTextPane lấy được tiêu điểm
            @Override
            public void focusGained(FocusEvent e) {
                // Kiểm tra nếu văn bản trong JTextPane là placeholder thì xóa nó
                if (inputTextPane.getText().equals(PLACE_HOLDER_TEXT)) {
                    inputTextPane.setText(""); // Xóa placeholder khi JTextPane lấy tiêu điểm
                }
            }

            // Ghi đè phương thức focusLost để xử lý khi JTextPane mất tiêu điểm
            @Override
            public void focusLost(FocusEvent e) {
                // Kiểm tra nếu văn bản trống khi mất tiêu điểm thì đặt lại placeholder
                if (inputTextPane.getText().isEmpty()) {
                    inputTextPane.setText(PLACE_HOLDER_TEXT); // Đặt lại placeholder khi JTextPane trống
                }
            }
        });

        // Xử lý sự kiện khi nhấn nút "Kiểm tra chính tả"
        checkSpellingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lấy văn bản từ inputTextPane
                String inputText = inputTextPane.getText();

                // Kiểm tra nếu văn bản là placeholder thì hiện thông báo và dừng thực thi
                if(inputText.equals(PLACE_HOLDER_TEXT)){
                    JOptionPane.showMessageDialog(MainForm.this, "Vui lòng nhập cụm từ cần kiểm tra!");
                    return;
                }

                // Tách văn bản thành các từ dựa trên regex
                String[] words = inputText.split(REGEX_SPLIT_TEXT);

                // Lấy StyledDocument của JTextPane để chỉnh sửa kiểu văn bản
                StyledDocument doc = inputTextPane.getStyledDocument();

                // Đặt lại văn bản gốc trong JTextPane
                inputTextPane.setText(inputText);

                // Biến lưu vị trí hiện tại trong văn bản
                int currentPos = 0;

                // Lặp qua từng từ trong văn bản
                for (String word : words) {
                    word = word.trim(); // Loại bỏ khoảng trắng dư thừa

                    try {
                        // Nếu từ không có trong từ điển và độ dài từ lớn hơn 0
                        if (!wordsDictionary.contains(word) && word.length() > 0) {
                            // Đổi màu từ sai chính tả thành đỏ
                            doc.setCharacterAttributes(currentPos, word.length(), redStyle, false);

                            // Hiển thị hộp thoại xác nhận thêm từ vào từ điển
                            int response = JOptionPane.showConfirmDialog(
                                MainForm.this, 
                                String.format("Từ \"%s\" chưa có trong từ điển. Bạn có muốn thêm từ này vào không?", word), 
                                "Xác nhận", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE
                            );

                            // Nếu người dùng đồng ý thêm từ vào từ điển
                            if(response == JOptionPane.YES_OPTION){
                                dictionaryManager.addWord(word); // Thêm từ vào từ điển
                                JOptionPane.showMessageDialog(MainForm.this, String.format("Từ \"%s\" đã được thêm vào từ điển", word));

                                // Cập nhật lại từ điển và đếm số từ
                                wordsDictionary = dictionaryManager.getDictionary();
                                wordsCountLabel.setText(String.format(formatWordCount, wordsDictionary.size()));
                            }
                        }

                        // Cập nhật vị trí hiện tại trong văn bản
                        currentPos += word.length() + 1;
                    } catch (Exception ex) {
                        // Hiển thị thông báo lỗi nếu có ngoại lệ xảy ra
                        JOptionPane.showMessageDialog(MainForm.this, ex.getMessage());
                    }
                }
            }
        });

        // Xử lý sự kiện khi nhấn nút "Tra từ"
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SearchForm(wordsCountLabel);
            }
        });

        // Xử lý sự kiện khi nhấn nút "Thêm từ mới"
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mở form để thêm từ mới và truyền vào wordsCountLabel
                new AddForm(wordsCountLabel);

                // Cập nhật lại từ điển sau khi thêm từ
                wordsDictionary = dictionaryManager.getDictionary();

                // Cập nhật số lượng từ vựng trong từ điển
                wordsCountLabel.setText(String.format(formatWordCount, wordsDictionary.size()));
            }
        });

        // Xử lý sự kiện khi nhấn nút "Tra từ"
        listButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new WordListForm(wordsDictionary);
            }
        });

        // Xử lý sự kiện khi nhấn nút "Giới thiệu"
        introduceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Tạo chuỗi văn bản giới thiệu về phần mềm
                String introductionText = "Thông tin phần mềm:\n";
                introductionText += "- Chức năng:\n";
                introductionText += "\t\t\t+ Kiểm tra chính tả của cụm từ/ đoạn văn\n";
                introductionText += "\t\t\t+ Tra cứu cụm từ/ đoạn văn\n";
                introductionText += "\t\t\t+ Thêm mới cụm từ/ đoạn văn\n";
                introductionText += "- Ngôn ngữ phát triển: Java\n";

                // Hiển thị hộp thoại giới thiệu về phần mềm
                JOptionPane.showMessageDialog(MainForm.this, introductionText, "Giới thiệu", 1);
            }
        });

        // Xử lý sự kiện khi nhấn nút "Thoát"
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Thoát khỏi chương trình
                System.exit(0);
            }
        });

        // Thêm text pane vào panel bên phải
        rightPanel.add(textPaneScrollPane, BorderLayout.CENTER);

        // Thêm panel trái và phải vào panel chính
        mainPanel.add(leftPanel, BorderLayout.WEST); 
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        // Thiết lập thông tin của JFrame
        add(mainPanel);
        setTitle("Spelling Check Software");
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
}