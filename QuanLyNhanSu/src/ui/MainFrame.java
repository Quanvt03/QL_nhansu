/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import javax.swing.*;
import java.awt.*;

// Lớp đại diện cho giao diện chính của ứng dụng
public class MainFrame extends JFrame {
    // Khai báo các nút điều khiển
    private JButton btnNhanVien, btnPhongBan, btnChucVu, btnLuong, btnHopDong, btnBaoHiem, btnDangXuat;
    private JPanel contentPanel; // Panel chính chứa nội dung các form

    // Constructor của lớp MainFrame
    public MainFrame() {
        setTitle("Quản lý nhân sự"); // Đặt tiêu đề cho cửa sổ chính
        setSize(800, 600); // Đặt kích thước cho cửa sổ chính
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Đặt hành động khi đóng cửa sổ
        setLayout(new BorderLayout()); // Thiết lập layout cho JFrame

        // Khởi tạo panel chứa các nút điều khiển
        JPanel panelButtons = new JPanel();
        panelButtons.setLayout(new GridLayout(7, 1, 10, 10)); // Thiết lập GridLayout cho panel
        panelButtons.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Đặt lề cho panel

        // Khởi tạo các nút điều khiển và thêm vào panel
        btnNhanVien = new JButton("Quản lý Nhân Viên");
        btnPhongBan = new JButton("Quản lý Phòng Ban");
        btnChucVu = new JButton("Quản lý Chức Vụ");
        btnLuong = new JButton("Quản lý Lương");
        btnHopDong = new JButton("Quản lý Hợp Đồng");
        btnBaoHiem = new JButton("Quản lý Bảo Hiểm");
        btnDangXuat = new JButton("Đăng Xuất");

        panelButtons.add(btnNhanVien);
        panelButtons.add(btnPhongBan);
        panelButtons.add(btnChucVu);
        panelButtons.add(btnLuong);
        panelButtons.add(btnHopDong);
        panelButtons.add(btnBaoHiem);
        panelButtons.add(btnDangXuat);

        // Thêm panel chứa các nút vào phần Tây của JFrame
        add(panelButtons, BorderLayout.WEST);

        // Khởi tạo panel chính chứa nội dung các form với CardLayout
        contentPanel = new JPanel(new CardLayout());
        add(contentPanel, BorderLayout.CENTER);

        // Tạo các form và thêm vào contentPanel
        NhanVienForm nhanVienForm = new NhanVienForm(this::showMainFrame);
        PhongBanForm phongBanForm = new PhongBanForm(this::showMainFrame);
        ChucVuForm chucVuForm = new ChucVuForm(this::showMainFrame);
        LuongForm luongForm = new LuongForm(this::showMainFrame);
        HopDongForm hopDongForm = new HopDongForm(this::showMainFrame);
        BaoHiemForm baoHiemForm = new BaoHiemForm(this::showMainFrame);

        // Thêm các form vào contentPanel với tên tương ứng
        contentPanel.add(nhanVienForm, "Nhân Viên");
        contentPanel.add(phongBanForm, "Phòng Ban");
        contentPanel.add(chucVuForm, "Chức Vụ");
        contentPanel.add(luongForm, "Lương");
        contentPanel.add(hopDongForm, "Hợp Đồng");
        contentPanel.add(baoHiemForm, "Bảo Hiểm");

        // Hiển thị form quản lý nhân viên khi khởi động
        showForm("Nhân Viên");

        // Thêm ActionListener cho các nút để chuyển đổi form
        btnNhanVien.addActionListener(e -> showForm("Nhân Viên"));
        btnPhongBan.addActionListener(e -> showForm("Phòng Ban"));
        btnChucVu.addActionListener(e -> showForm("Chức Vụ"));
        btnLuong.addActionListener(e -> showForm("Lương"));
        btnHopDong.addActionListener(e -> showForm("Hợp Đồng"));
        btnBaoHiem.addActionListener(e -> showForm("Bảo Hiểm"));
        btnDangXuat.addActionListener(e -> {
            setVisible(false); // Ẩn cửa sổ chính
            LoginForm loginForm = new LoginForm(); // Tạo và hiển thị form đăng nhập
            loginForm.setVisible(true);
        });
    }

    // Phương thức để hiển thị form theo tên
    private void showForm(String formName) {
        CardLayout cl = (CardLayout) (contentPanel.getLayout()); // Lấy CardLayout từ contentPanel
        cl.show(contentPanel, formName); // Hiển thị form theo tên
    }

    // Phương thức để hiện lại MainFrame
    private void showMainFrame() {
        setVisible(true); // Hiển thị MainFrame
    }

    // Phương thức main để khởi động ứng dụng
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true)); // Khởi tạo và hiển thị MainFrame trên luồng sự kiện
    }
}
