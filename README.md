# Ứng dụng Mua bán sản phẩm Online (Full Stack)

Đây là một dự án ứng dụng Android kết nối với Backend Express.js để xây dựng một hệ thống mua bán sản phẩm hoàn chỉnh. Ứng dụng bao gồm chức năng đăng nhập, đăng ký tài khoản được kích hoạt bằng OTP qua email, xem danh mục sản phẩm, và duyệt sản phẩm theo loại với lazy loading.

## 📱 Ảnh chụp màn hình

|        Màn hình Intro             |        Màn hình đăng nhập         |         Màn hình đăng ký          |           Màn hình OTP            |      Màn hình quên mật khẩu       |      Màn hình chính       |
| :-------------------------------: | :-------------------------------: | :-------------------------------: | :-------------------------------: | :-------------------------------: | :-------------------------------: |
| <img src="img/5.png" width="200"> | <img src="img/1.png" width="200"> | <img src="img/2.png" width="200"> | <img src="img/3.png" width="200"> | <img src="img/4.png" width="200"> | <img src="img/6.png" width="200"> |

## ✨ Tính năng

### 1. Màn hình Intro
- Hiển thị trang giới thiệu ứng dụng
- Nút "Bắt đầu" để chuyển đến đăng nhập
- Tự động chuyển đến trang chính nếu đã đăng nhập trước đó

### 2. Xác thực người dùng
- **Đăng nhập:** Nhập username/email và password, hỗ trợ toggle hiển thị mật khẩu
- **Đăng ký:** Tạo tài khoản mới với xác minh OTP 6 chữ số
- **Quên mật khẩu:** Đặt lại mật khẩu thông qua email OTP
- Lưu thông tin người dùng vào SharedPreferences sau khi đăng nhập thành công

### 3. Danh mục sản phẩm
- Hiển thị danh sách danh mục theo chiều ngang (RecyclerView)
- Lấy dữ liệu từ API: `GET /api/categories`
- Click vào danh mục để lọc sản phẩm theo loại

### 4. Sản phẩm
- Hiển thị sản phẩm dạng lưới (Grid 2 cột)
- Sắp xếp sản phẩm tăng dần theo giá
- **Lazy Loading:** Tự động load thêm sản phẩm khi cuộn xuống cuối màn hình
- Pagination: 10 sản phẩm mỗi trang
- Lấy dữ liệu từ API: `GET /api/products/category/{categoryId}?page={page}&limit={limit}`

### 5. Thông tin người dùng
- Hiển thị tên người dùng ở phần header của trang chính
- Nút Profile, Cart, Support, Settings trong bottom navigation

## 🛠️ Công nghệ sử dụng

### Frontend (Android)
- **Android Java** - Ngôn ngữ lập trình
- **XML** - Định nghĩa giao diện
- **Retrofit 2** - HTTP client library
- **Glide** - Xử lý hình ảnh
- **RecyclerView** - Hiển thị danh sách
- **Gson** - JSON parser
- **Material Design** - Thiết kế UI

### Backend (Node.js)
- **Express.js** - Framework web
- **MongoDB** - Database
- **Mongoose** - MongoDB ODM
- **JWT** - Xác thực
- **Bcrypt** - Mã hóa password

## 📋 Yêu cầu

### Android
- Android 5.0 (API level 21) hoặc cao hơn
- Android Studio 4.0 hoặc cao hơn

### Backend
- Node.js 14.0 hoặc cao hơn
- MongoDB Atlas (hoặc MongoDB local)

## 🚀 Cài đặt và chạy

### Backend

1.  **Cài đặt dependencies:**
    ```bash
    npm install
    ```

2.  **Cấu hình .env:**
    ```
    MONGODB_URI="mongodb+srv://username:password@cluster.mongodb.net/dbname?retryWrites=true&w=majority"
    PORT=5001
    ```

3.  **Khởi chạy server:**
    ```bash
    npm start
    ```
    Server sẽ chạy tại `http://localhost:5001`

4.  **Seed dữ liệu mẫu:**
    ```bash
    node seed.js
    ```

### Android

1.  Mở dự án trong Android Studio
2.  Đợi Gradle sync xong
3.  Cấu hình Retrofit: Base URL = `http://10.0.2.2:5001/` (dành cho Android Emulator)
4.  Chạy ứng dụng trên trình giả lập (AVD) hoặc thiết bị vật lý

## 📊 Cấu trúc cơ sở dữ liệu

### Users
```json
{
  "name": "Tên người dùng",
  "username": "username",
  "email": "email@example.com",
  "password": "hashed_password"
}
```

### Categories
```json
{
  "title": "Danh mục",
  "picUrl": "https://..."
}
```

### Products
```json
{
  "title": "Tên sản phẩm",
  "description": "Mô tả",
  "picUrl": ["url1", "url2"],
  "price": 29.99,
  "oldPrice": 39.99,
  "rating": 4.5,
  "categoryId": "ObjectId"
}
```

## 🔌 API Endpoints

### Authentication
- `POST /api/auth/register` - Đăng ký
- `POST /api/auth/login` - Đăng nhập
- `GET /api/auth/profile` - Lấy thông tin người dùng

### Categories
- `GET /api/categories` - Lấy tất cả danh mục

### Products
- `GET /api/products` - Lấy tất cả sản phẩm
- `GET /api/products/category/:categoryId` - Lấy sản phẩm theo danh mục (có pagination)

## 📝 Hướng dẫn sử dụng

1. **Mở ứng dụng:** Hiển thị trang Intro
2. **Nhấn "Bắt đầu":**
   - Nếu chưa đăng nhập → Chuyển đến Login
   - Nếu đã đăng nhập → Chuyển đến Main
3. **Đăng nhập hoặc đăng ký:** Sử dụng email hoặc username
4. **Xem sản phẩm:** Click vào danh mục, cuộn để load thêm

## 📌 Lưu ý

- Ứng dụng lưu token và thông tin người dùng vào SharedPreferences
- Tất cả hình ảnh sản phẩm được load thông qua Glide
- Backend được cấu hình để xử lý CORS và cleartext traffic từ Android Emulator
- Để test trên thiết bị thật, thay URL `10.0.2.2:5001` thành IP thực của server

## 👥 Thành viên Nhóm 02

- 22162003	Nguyễn Sỹ Thế Anh
- 22162026	Huỳnh Lê Minh
- 22162054	Nguyễn Kim Tú
- 22162055	Chống Lệ Vân

