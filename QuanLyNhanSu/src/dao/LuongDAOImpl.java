/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao; 

import model.Luong; 

import java.sql.Connection; // Import lớp Connection để làm việc với kết nối cơ sở dữ liệu
import java.sql.PreparedStatement; // Import lớp PreparedStatement để thực thi các câu lệnh SQL có tham số
import java.sql.ResultSet; // Import lớp ResultSet để lưu trữ và thao tác với kết quả truy vấn
import java.sql.SQLException; // Import lớp SQLException để xử lý các lỗi liên quan đến SQL
import java.util.ArrayList; // Import lớp ArrayList để lưu trữ danh sách các đối tượng Luong
import java.util.List; // Import lớp List để làm việc với danh sách các đối tượng Luong

public class LuongDAOImpl implements LuongDAO { // Định nghĩa lớp LuongDAOImpl thực hiện các phương thức từ giao diện LuongDAO
    private Connection connection; // Khai báo đối tượng Connection để kết nối đến cơ sở dữ liệu

    public LuongDAOImpl(Connection connection) { // Constructor để khởi tạo đối tượng LuongDAOImpl và thiết lập kết nối cơ sở dữ liệu
        this.connection = connection; // Gán kết nối cơ sở dữ liệu
    }

    @Override
    public void addLuong(Luong luong) throws SQLException { // Phương thức thêm một đối tượng Luong vào cơ sở dữ liệu
        String query = "INSERT INTO luong (ma_nhan_vien, ho_ten, luong, ung_luong) VALUES (?, ?, ?, ?)"; // Câu lệnh SQL để thêm dữ liệu vào bảng luong
        try (PreparedStatement stmt = connection.prepareStatement(query)) { // Tạo đối tượng PreparedStatement để thực thi câu lệnh SQL
            stmt.setInt(1, luong.getMaNhanVien()); // Đặt giá trị cho tham số ma_nhan_vien
            stmt.setString(2, luong.getHoTen()); // Đặt giá trị cho tham số ho_ten
            stmt.setDouble(3, luong.getLuong()); // Đặt giá trị cho tham số luong
            stmt.setDouble(4, luong.getUngLuong()); // Đặt giá trị cho tham số ung_luong
            stmt.executeUpdate(); // Thực thi câu lệnh SQL
        } catch (SQLException e) { // Bắt lỗi SQLException nếu có lỗi trong khi thực thi
            throw new SQLException("Error adding Luong", e); // Ném lỗi SQLException nếu có lỗi
        }
    }

    @Override
    public void updateLuong(Luong luong) throws SQLException { // Phương thức cập nhật một đối tượng Luong trong cơ sở dữ liệu
        String query = "UPDATE luong SET ho_ten = ?, luong = ?, ung_luong = ? WHERE ma_nhan_vien = ?"; // Câu lệnh SQL để cập nhật dữ liệu trong bảng luong
        try (PreparedStatement stmt = connection.prepareStatement(query)) { // Tạo đối tượng PreparedStatement để thực thi câu lệnh SQL
            stmt.setString(1, luong.getHoTen()); // Đặt giá trị cho tham số ho_ten
            stmt.setDouble(2, luong.getLuong()); // Đặt giá trị cho tham số luong
            stmt.setDouble(3, luong.getUngLuong()); // Đặt giá trị cho tham số ung_luong
            stmt.setInt(4, luong.getMaNhanVien()); // Đặt giá trị cho tham số ma_nhan_vien
            stmt.executeUpdate(); // Thực thi câu lệnh SQL
        } catch (SQLException e) { // Bắt lỗi SQLException nếu có lỗi trong khi thực thi
            throw new SQLException("Error updating Luong", e); // Ném lỗi SQLException nếu có lỗi
        }
    }

    @Override
    public void deleteLuong(int maNhanVien) throws SQLException { // Phương thức xóa một đối tượng Luong dựa trên ma_nhan_vien
        String query = "DELETE FROM luong WHERE ma_nhan_vien = ?"; // Câu lệnh SQL để xóa dữ liệu từ bảng luong
        try (PreparedStatement stmt = connection.prepareStatement(query)) { // Tạo đối tượng PreparedStatement để thực thi câu lệnh SQL
            stmt.setInt(1, maNhanVien); // Đặt giá trị cho tham số ma_nhan_vien
            stmt.executeUpdate(); // Thực thi câu lệnh SQL
        } catch (SQLException e) { // Bắt lỗi SQLException nếu có lỗi trong khi thực thi
            throw new SQLException("Error deleting Luong", e); // Ném lỗi SQLException nếu có lỗi
        }
    }

    @Override
    public Luong getLuongById(int maNhanVien) throws SQLException { // Phương thức lấy thông tin Luong dựa trên ma_nhan_vien
        String query = "SELECT * FROM luong WHERE ma_nhan_vien = ?"; // Câu lệnh SQL để lấy thông tin từ bảng luong
        try (PreparedStatement stmt = connection.prepareStatement(query)) { // Tạo đối tượng PreparedStatement để thực thi câu lệnh SQL
            stmt.setInt(1, maNhanVien); // Đặt giá trị cho tham số ma_nhan_vien
            try (ResultSet rs = stmt.executeQuery()) { // Thực thi câu lệnh SQL và lấy kết quả
                if (rs.next()) { // Nếu có kết quả
                    return new Luong(
                        rs.getInt("ma_nhan_vien"), // Lấy giá trị ma_nhan_vien
                        rs.getString("ho_ten"), // Lấy giá trị ho_ten
                        rs.getDouble("luong"), // Lấy giá trị luong
                        rs.getDouble("ung_luong") // Lấy giá trị ung_luong
                    );
                }
            }
        } catch (SQLException e) { // Bắt lỗi SQLException nếu có lỗi trong khi thực thi
            throw new SQLException("Error retrieving Luong", e); // Ném lỗi SQLException nếu có lỗi
        }
        return null; // Trả về null nếu không tìm thấy thông tin
    }

    @Override
    public List<Luong> getAllLuongs() throws SQLException { // Phương thức lấy tất cả các đối tượng Luong từ cơ sở dữ liệu
        List<Luong> luongs = new ArrayList<>(); // Tạo danh sách để lưu trữ các đối tượng Luong
        String query = "SELECT * FROM luong"; // Câu lệnh SQL để lấy tất cả dữ liệu từ bảng luong
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) { // Tạo đối tượng PreparedStatement và thực thi câu lệnh SQL
            while (rs.next()) { // Lặp qua các kết quả trả về
                luongs.add(new Luong(
                    rs.getInt("ma_nhan_vien"), // Lấy giá trị ma_nhan_vien
                    rs.getString("ho_ten"), // Lấy giá trị ho_ten
                    rs.getDouble("luong"), // Lấy giá trị luong
                    rs.getDouble("ung_luong") // Lấy giá trị ung_luong
                ));
            }
        } catch (SQLException e) { // Bắt lỗi SQLException nếu có lỗi trong khi thực thi
            throw new SQLException("Error retrieving all Luongs", e); // Ném lỗi SQLException nếu có lỗi
        }
        return luongs; // Trả về danh sách các đối tượng Luong
    }

    @Override
    public List<Luong> searchLuongByMaNhanVien(int maNhanVien) throws SQLException { // Phương thức tìm kiếm các đối tượng Luong dựa trên ma_nhan_vien
        List<Luong> luongs = new ArrayList<>(); // Tạo danh sách để lưu trữ các đối tượng Luong
        String query = "SELECT * FROM luong WHERE ma_nhan_vien = ?"; // Câu lệnh SQL để tìm kiếm dữ liệu trong bảng luong
        try (PreparedStatement stmt = connection.prepareStatement(query)) { // Tạo đối tượng PreparedStatement để thực thi câu lệnh SQL
            stmt.setInt(1, maNhanVien); // Đặt giá trị cho tham số ma_nhan_vien
            try (ResultSet rs = stmt.executeQuery()) { // Thực thi câu lệnh SQL và lấy kết quả
                while (rs.next()) { // Lặp qua các kết quả trả về
                    luongs.add(new Luong(
                        rs.getInt("ma_nhan_vien"), // Lấy giá trị ma_nhan_vien
                        rs.getString("ho_ten"), // Lấy giá trị ho_ten
                        rs.getDouble("luong"), // Lấy giá trị luong
                        rs.getDouble("ung_luong") // Lấy giá trị ung_luong
                    ));
                }
            }
        } catch (SQLException e) { // Bắt lỗi SQLException nếu có lỗi trong khi thực thi
            throw new SQLException("Error searching Luong by maNhanVien", e); // Ném lỗi SQLException nếu có lỗi
        }
        return luongs; // Trả về danh sách các đối tượng Luong tìm được
    }
}
