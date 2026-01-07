# UTE PhoneHub - Mobile App

Ứng dụng bán điện thoại di động hiện đại được xây dựng bằng **Android** với **Material Design 3**, kết nối với backend Java Spring Boot.

## 🚀 Tính Năng Chính

### 1. **Xác Thực & Tài Khoản**
- Đăng nhập / Đăng ký với email
- Quản lý phiên đăng nhập với JWT token
- Lưu trữ token an toàn trong SharedPreferences
- Hỗ trợ đăng xuất

### 2. **Duyệt Sản Phẩm**
- Xem danh sách sản phẩm phân trang
- Tìm kiếm sản phẩm theo từ khóa
- **Filter theo thương hiệu (Brand)** với dropdown scrollable
- Sắp xếp sản phẩm (mới nhất, giá thấp/cao, tên A-Z)
- Xem chi tiết sản phẩm với:
  - Hình ảnh từ URL
  - Mô tả chi tiết
  - Thông số kỹ thuật (JSON format)
  - Đánh giá của khách hàng
  - Tùy chọn thêm vào giỏ hàng

### 3. **Hệ Thống Đánh Giá**
- Xem đánh giá từ khách hàng khác
- Đánh giá sản phẩm từ 1-5 sao kèm bình luận
- Thích/Bỏ thích đánh giá của người khác
- Hiển thị tên người dùng, ngày đánh giá

### 4. **Quản Lý Giỏ Hàng**
- Thêm/xóa sản phẩm vào giỏ hàng
- Chỉnh sửa số lượng sản phẩm
- Tính tổng tiền tạm tính
- Hiển thị giảm giá nếu có

### 5. **Thanh Toán**
- Xem chi tiết giỏ hàng trước khi thanh toán
- Chọn địa chỉ giao hàng
- Áp dụng mã giảm giá (voucher)
- Xem tóm tắt đơn hàng

### 6. **Mã Giảm Giá (Vouchers)**
- Xem danh sách vouchers khả dụng
- Áp dụng voucher vào đơn hàng
- Xem điều kiện và chi tiết voucher
- Fallback hiển thị demo data nếu API lỗi

### 7. **Hồ Sơ Người Dùng**
- Xem thông tin tài khoản
- Xem lịch sử đơn hàng
- Xem trạng thái đơn hàng

## 📱 Công Nghệ Sử Dụng

### Frontend (Android)
- **Language**: Java
- **Android Version**: Min SDK 28 (Android 9.0)
- **UI Framework**: Material Design 3
- **Architecture**: MVVM + Repository Pattern

### Libraries
- **Retrofit2**: HTTP client cho REST API
- **Glide**: Loading hình ảnh
- **Gson**: JSON serialization/deserialization
- **Material Components**: UI components
- **androidx.appcompat**: Support library
- **androidx.fragment**: Fragment management
- **RecyclerView**: List views
- **ViewPager2**: Image carousel

### Backend
- **API Base URL**: `http://10.0.2.2:8080` (emulator) hoặc `http://10.0.0.2:8080` (real device)
- **Authentication**: JWT Bearer Token
- **API Version**: v1

## 🏗️ Cấu Trúc Dự Án

```
app/src/main/
├── java/com/example/ute/
│   ├── activities/
│   │   ├── MainActivity.java              # Activity chính với bottom navigation
│   │   ├── LoginActivity.java             # Màn hình đăng nhập
│   │   ├── SplashActivity.java            # Splash screen
│   │   ├── ProductListActivity.java       # Danh sách sản phẩm
│   │   ├── ProductDetailActivity.java     # Chi tiết sản phẩm & đánh giá
│   │   ├── CheckoutActivity.java          # Thanh toán
│   │   ├── SearchActivity.java            # Tìm kiếm sản phẩm
│   │   └── OrderTrackingActivity.java     # Theo dõi đơn hàng
│   │
│   ├── fragments/
│   │   ├── HomeFragment.java              # Trang chủ
│   │   ├── CartFragment.java              # Giỏ hàng
│   │   ├── VouchersFragment.java          # Danh sách vouchers
│   │   ├── CategoriesFragment.java        # Danh mục
│   │   └── ProfileFragment.java           # Hồ sơ người dùng
│   │
│   ├── adapters/
│   │   ├── ProductAdapter.java            # Adapter cho RecyclerView sản phẩm
│   │   ├── CartAdapter.java               # Adapter cho giỏ hàng
│   │   ├── ReviewAdapter.java             # Adapter cho danh sách đánh giá
│   │   ├── CategoryAdapter.java           # Adapter cho danh mục
│   │   ├── BannerAdapter.java             # Adapter cho banner carousel
│   │   ├── VoucherAdapter.java            # Adapter cho vouchers
│   │   ├── BrandFilterAdapter.java        # Adapter cho filter brand (NEW)
│   │   └── SpecificationAdapter.java      # Adapter cho thông số kỹ thuật
│   │
│   ├── models/
│   │   ├── Product.java
│   │   ├── CartItem.java
│   │   ├── Cart.java
│   │   ├── Review.java
│   │   ├── User.java
│   │   ├── Voucher.java
│   │   ├── Order.java
│   │   ├── Category.java
│   │   └── Brand.java
│   │
│   ├── models/response/
│   │   ├── ApiResponse.java
│   │   ├── CartResponse.java
│   │   ├── ProductListResponse.java
│   │   ├── ReviewListResponse.java
│   │   ├── CartDataResponse.java
│   │   ├── VoucherResponse.java
│   │   └── BrandResponse.java
│   │
│   ├── models/request/
│   │   ├── LoginRequest.java
│   │   ├── RegisterRequest.java
│   │   ├── AddToCartRequest.java
│   │   ├── UpdateCartRequest.java
│   │   ├── ReviewRequest.java
│   │   └── CheckoutRequest.java
│   │
│   ├── services/
│   │   ├── ApiService.java                # Retrofit interface
│   │   └── ApiClient.java                 # Retrofit client setup
│   │
│   ├── utils/
│   │   ├── SessionManager.java            # JWT token management
│   │   ├── PriceFormatter.java            # Định dạng giá tiền
│   │   ├── DateUtils.java                 # Xử lý ngày tháng
│   │   └── Constants.java                 # Hằng số toàn ứng dụng
│   │
│   └── config/
│       └── MyAppGlideModule.java          # Glide configuration
│
└── res/
    ├── layout/
    │   ├── activity_*.xml                 # Activities layout
    │   ├── fragment_*.xml                 # Fragments layout
    │   ├── item_*.xml                     # Item layouts
    │   └── dialog_*.xml                   # Dialog layouts
    │
    ├── drawable/                          # Vector drawables
    ├── mipmap/                            # App icon
    ├── values/
    │   ├── colors.xml
    │   ├── strings.xml                    # Text resources (Tiếng Việt)
    │   ├── dimens.xml
    │   └── styles.xml
    └── menu/                              # Bottom navigation menu
```

## 🔐 API Endpoints

### Xác Thực
- `POST /api/v1/auth/register` - Đăng ký
- `POST /api/v1/auth/login` - Đăng nhập

### Sản Phẩm
- `GET /api/v1/products?page={page}&limit={limit}` - Danh sách sản phẩm
- `GET /api/v1/products?page={page}&limit={limit}&categoryId={id}&brandId={id}&sortBy={sort}` - Danh sách sản phẩm với filter
- `GET /api/v1/products/{id}` - Chi tiết sản phẩm
- `GET /api/v1/products/search?keyword={keyword}&page={page}` - Tìm kiếm

### Brand (Thương Hiệu) - NEW
- `GET /api/v1/brands` - Lấy danh sách thương hiệu

### Đánh Giá
- `GET /api/v1/reviews?productId={id}&page={page}&limit={limit}` - Danh sách đánh giá
- `POST /api/v1/reviews?productId={id}` - Tạo đánh giá (yêu cầu JWT)
- `POST /api/v1/reviews/{id}/like` - Thích đánh giá (yêu cầu JWT)
- `DELETE /api/v1/reviews/{id}/like` - Bỏ thích đánh giá (yêu cầu JWT)

### Giỏ Hàng
- `GET /api/v1/cart` - Lấy giỏ hàng (yêu cầu JWT)
- `POST /api/v1/cart` - Thêm vào giỏ hàng (yêu cầu JWT)
- `PUT /api/v1/cart/items/{cartItemId}` - Cập nhật số lượng (yêu cầu JWT)
- `DELETE /api/v1/cart/items/{cartItemId}` - Xóa sản phẩm (yêu cầu JWT)

### Mã Giảm Giá
- `GET /api/v1/vouchers?page={page}&limit={limit}` - Danh sách vouchers
- `POST /api/v1/cart/vouchers` - Áp dụng voucher (yêu cầu JWT)

### Thanh Toán
- `POST /api/v1/orders` - Tạo đơn hàng (yêu cầu JWT)
- `GET /api/v1/orders` - Danh sách đơn hàng (yêu cầu JWT)
- `GET /api/v1/orders/{id}` - Chi tiết đơn hàng (yêu cầu JWT)

## 🔑 Xác Thực API

Tất cả endpoints cần xác thực (trừ login/register) đều yêu cầu JWT token trong header:

```
Authorization: Bearer {token}
```

Token được lưu tự động sau khi đăng nhập qua `SessionManager.java`

## 🛠️ Cài Đặt & Chạy

### Yêu Cầu
- Android Studio (Giraffe hoặc mới hơn)
- Android SDK 28+
- Gradle 8.0+
- Java 17+

### Bước 1: Clone Repository
```bash
git clone https://github.com/tuantujr/FinalAndroid.git
cd UTE
```

### Bước 2: Mở trong Android Studio
- Mở Android Studio
- File → Open → Chọn folder UTE
- Để Android Studio sync Gradle files

### Bước 3: Cấu Hình Backend URL
Chỉnh sửa [ApiClient.java](app/src/main/java/com/example/ute/services/ApiClient.java):
```java
private static final String BASE_URL = "http://10.0.2.2:8080/"; // Emulator
// hoặc
private static final String BASE_URL = "http://10.0.0.2:8080/"; // Real device
```

### Bước 4: Chạy Ứng Dụng
- Kết nối emulator hoặc device
- Click "Run" trong Android Studio
- Hoặc chạy lệnh:
```bash
./gradlew installDebug
```

## 📋 Hướng Dẫn Sử Dụng

### 1. Đăng Nhập
1. Mở ứng dụng
2. Nhập email & mật khẩu
3. Nhấn "Đăng Nhập"
4. Nếu thành công, chuyển đến trang chủ

### 2. Duyệt Sản Phẩm
1. Tại trang chủ, cuộn xuống xem danh sách sản phẩm
2. Nhấn vào sản phẩm để xem chi tiết
3. Xem hình ảnh, mô tả, thông số kỹ thuật
4. Xem đánh giá từ khách hàng khác

### 3. Viết Đánh Giá
1. Trong trang chi tiết sản phẩm
2. Nhấn nút "Viết đánh giá"
3. Chọn số sao (1-5)
4. Nhập bình luận
5. Nhấn "Gửi đánh giá"

### 4. Thêm Vào Giỏ Hàng
1. Trong trang chi tiết sản phẩm
2. Điều chỉnh số lượng (nút +/-)
3. Nhấn "Thêm vào giỏ"
4. Xem thông báo thành công

### 5. Thanh Toán
1. Nhấn icon giỏ hàng ở bottom navigation
2. Xem danh sách sản phẩm
3. Có thể chỉnh sửa số lượng hoặc xóa
4. Nhấn "Tiếp tục thanh toán"
5. Chọn địa chỉ giao hàng
6. Áp dụng mã giảm giá (nếu có)
7. Nhấn "Hoàn tất đơn hàng"

### 6. Sử Dụng Mã Giảm Giá
1. Nhấn tab "Vouchers" ở bottom navigation
2. Xem danh sách mã giảm giá khả dụng
3. Chọn mã muốn áp dụng
4. Áp dụng trong quá trình thanh toán

## 🐛 Gỡ Lỗi

### Không Kết Nối Được Backend
- Kiểm tra URL trong `ApiClient.java`
- Đảm bảo emulator/device có thể ping tới IP của backend
- Kiểm tra backend service đang chạy

### API trả về 401 Unauthorized
- Kiểm tra token đã được lưu: `SessionManager.getAuthToken()`
- Đăng nhập lại để cấp token mới
- Kiểm tra token format: `Authorization: Bearer {token}`

### Hình ảnh Không Hiển Thị
- Kiểm tra URL của hình ảnh có đúng không
- Xác nhận network connectivity
- Kiểm tra Glide cache: `Glide.get(context).clearMemory()`

## 📊 Logs & Debugging

Để xem logs chi tiết, mở Android Studio Logcat:
```bash
adb logcat | grep "ProductDetail\|CartFragment\|ReviewAdapter"
```

## 🎨 Thiết Kế

- **Color Scheme**: Material Design 3 (Primary, Secondary, Tertiary)
- **Typography**: Roboto font
- **Icons**: Material Icons
- **Layout**: Constraint Layout

## 📱 Tương Thích

- **Minimum SDK**: Android 9.0 (API 28)
- **Target SDK**: Android 14 (API 34)
- **Supported Devices**: Phone & Tablet

## 🚀 Build Release

### Tạo APK Release
```bash
./gradlew assembleRelease
```
APK được đặt tại: `app/build/outputs/apk/release/app-release.apk`

### Ký APK với Keystore
```bash
jarsigner -verbose -sigalg SHA1withRSA -digestalg SHA1 \
  -keystore my-release-key.keystore \
  app-release-unsigned.apk alias_name
```

## 📦 Dependencies

```gradle
dependencies {
    // Core
    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'androidx.fragment:fragment:1.6.1'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    
    // Material Design 3
    implementation 'com.google.android.material:material:1.10.0'
    
    // Networking
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
    implementation 'com.squareup.okhttp3:okhttp:4.11.0'
    implementation 'com.squareup.okhttp3:logging-interceptor:4.11.0'
    
    // Image Loading
    implementation 'com.github.bumptech.glide:glide:4.15.1'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.15.1'
    
    // JSON
    implementation 'com.google.code.gson:gson:2.10.1'
    
    // RecyclerView & ViewPager
    implementation 'androidx.recyclerview:recyclerview:1.3.1'
    implementation 'androidx.viewpager2:viewpager2:1.0.0'
}
```

## 📄 Giấy Phép

MIT License - Xem [LICENSE](LICENSE) để chi tiết

## 👨‍💻 Author

**UTE PhoneHub Team**
- Email: support@utephonehub.me
- Website: [www.utephonehub.me](https://www.utephonehub.me)

## 🤝 Đóng Góp

Chúng tôi hoan nghênh các pull request và suggestions. Vui lòng:
1. Fork repository
2. Tạo feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Mở Pull Request

## 📞 Hỗ Trợ

Nếu gặp vấn đề, vui lòng:
1. Kiểm tra [Issues](../../issues)
2. Tạo issue mới nếu chưa có
3. Liên hệ qua email: support@utephonehub.me

---

**Status**: ✅ Production Ready

**Last Updated**: January 2026
