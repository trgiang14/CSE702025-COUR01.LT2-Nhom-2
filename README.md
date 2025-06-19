# Phần mềm Quản lý Kho hàng

Phần mềm quản lý kho hàng được xây dựng bằng Java Swing, cho phép người dùng quản lý sản phẩm, đơn hàng xuất/nhập và thống kê báo cáo.Dự án "Hệ thống quản lý kho hàng" nhằm xây dựng một phần mềm hỗ trợ doanh nghiệp theo dõi, quản lý và tối ưu hóa quá trình nhập - xuất - tồn kho. Hệ thống cho phép cập nhật thông tin hàng hóa theo thời gian thực, bao gồm mã hàng, tên sản phẩm, số lượng, vị trí lưu trữ và trạng thái tồn kho. Ngoài ra, phần mềm còn tích hợp các chức năng kiểm kê, cảnh báo tồn kho thấp, thống kê báo cáo và phân quyền người dùng. Mục tiêu của dự án là giúp doanh nghiệp giảm thiểu sai sót trong quản lý thủ công, tiết kiệm chi phí và nâng cao hiệu quả vận hành kho hàng.

## Tính năng chính

1. **Quản lý sản phẩm**

   - Thêm, sửa, xóa sản phẩm
   - Quản lý thông tin: mã, tên, danh mục, nhà cung cấp, giá, số lượng
   - Tìm kiếm sản phẩm

2. **Quản lý đơn hàng xuất**

   - Tạo đơn hàng xuất
   - Thêm/xóa sản phẩm vào đơn hàng
   - Tính tổng tiền tự động
   - Xuất báo cáo đơn hàng

3. **Quản lý đơn hàng nhập**

   - Tạo đơn nhập hàng
   - Thêm/xóa sản phẩm vào đơn nhập
   - Tính tổng tiền tự động
   - Xuất báo cáo nhập hàng

4. **Thống kê và báo cáo**
   - Thống kê sản phẩm theo danh mục/nhà cung cấp
   - Thống kê doanh thu theo tháng
   - Thống kê chi phí nhập hàng
   - Top 5 sản phẩm tồn kho nhiều nhất
   - Dashboard tổng quan
   - Lọc thống kê theo năm

## Yêu cầu hệ thống

- Java Runtime Environment (JRE) 8 trở lên
- Maven (nếu build từ source code)

## Hướng dẫn cài đặt và chạy

### Cách 1: Chạy bằng IDE

1. Mở project bằng IDE (Eclipse/IntelliJ IDEA/NetBeans)

2. Cài đặt các dependency bằng Maven:

```bash
mvn clean install
```

4. Chạy file `src/main/java/com/example/qlkho/App.java`

### Cách 2: Chạy file JAR

1. Tải file JAR từ release mới nhất

2. Mở Terminal/Command Prompt và chạy lệnh:

```bash
java -jar qlkho.jar
```

## Tài khoản đăng nhập

- Tên tài khoản: admin
- Mật khẩu: admin

## Cấu trúc thư mục

```
src/main/java/com/example/qlkho/
├── controller/     # Các controller xử lý logic
├── dao/           # Data Access Objects
├── entity/        # Các model/entity
├── utils/         # Các utility class
├── view/          # Giao diện người dùng
└── App.java       # Entry point
```

## Lưu trữ dữ liệu

Dữ liệu được lưu trữ dưới dạng XML trong thư mục `src/main/resources`:

- `products.xml`: Danh sách sản phẩm
- `orders.xml`: Đơn hàng xuất
- `orderDetails.xml`: Chi tiết đơn hàng xuất
- `importOrders.xml`: Đơn hàng nhập
- `importOrderDetails.xml`: Chi tiết đơn hàng nhập

## Thư viện sử dụng

- **JFreeChart**: Tạo biểu đồ thống kê
- **JAXB**: Xử lý XML
- **JCalendar**: Component chọn ngày tháng
- **Apache POI**: Xuất báo cáo Excel
