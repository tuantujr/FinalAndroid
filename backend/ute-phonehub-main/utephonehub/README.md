# UTE Phone Hub Android App

## Overview
MVVM Architecture-based Android application for UTE Phone Hub e-commerce platform. The app displays products from the backend API and allows users to view product details.

## Architecture
The app follows the MVVM (Model-View-ViewModel) pattern:

```
View (Activities/Fragments)
       ↓
ViewModel (Business Logic)
       ↓
Repository (Data Access)
       ↓
API Service (Retrofit)
```

## Project Structure

```
utephonehub/
├── src/main/
│   ├── java/com/utephonehub/
│   │   ├── activity/          # UI Activities
│   │   │   ├── MainActivity.java
│   │   │   └── ProductDetailActivity.java
│   │   ├── adapter/           # RecyclerView Adapters
│   │   │   └── ProductAdapter.java
│   │   ├── api/               # API Service & Retrofit
│   │   │   ├── ApiService.java
│   │   │   └── RetrofitClient.java
│   │   ├── model/             # Data Models
│   │   │   ├── Product.java
│   │   │   ├── Category.java
│   │   │   ├── Brand.java
│   │   │   ├── ApiResponse.java
│   │   │   └── ProductListResponse.java
│   │   ├── repository/        # Repository Pattern
│   │   │   └── ProductRepository.java
│   │   └── viewmodel/         # ViewModels
│   │       └── ProductViewModel.java
│   ├── res/
│   │   ├── layout/            # XML Layouts
│   │   │   ├── activity_main.xml
│   │   │   ├── activity_product_detail.xml
│   │   │   └── item_product.xml
│   │   ├── drawable/          # Drawable Resources
│   │   ├── values/            # String, Color, Style resources
│   │   └── ...
│   └── AndroidManifest.xml
├── build.gradle               # Dependencies & Build Config
└── README.md                  # This file
```

## Features

### Current Implementation
1. **Product List Screen**
   - Display products in a 2-column grid layout
   - Pull-to-refresh functionality
   - Pagination (loads more on scroll)
   - Search functionality (ready for implementation)

2. **Product Detail Screen**
   - Full product information display
   - Product image
   - Price, stock, category, brand details
   - Add to cart button (ready for implementation)

### To Be Implemented
- User Authentication (Login/Register)
- Shopping Cart Management
- Checkout Process
- User Profile
- Order History
- Reviews & Ratings
- Wishlist

## Dependencies

### Core AndroidX Libraries
- `androidx.appcompat:appcompat:1.6.1` - AppCompat support
- `androidx.lifecycle:lifecycle-viewmodel:2.6.2` - ViewModel
- `androidx.lifecycle:lifecycle-livedata:2.6.2` - LiveData
- `androidx.recyclerview:recyclerview:1.3.2` - RecyclerView
- `androidx.swiperefreshlayout:swiperefreshlayout:1.1.0` - Pull-to-refresh

### Networking
- `com.squareup.retrofit2:retrofit:2.9.0` - HTTP Client
- `com.squareup.retrofit2:converter-gson:2.9.0` - JSON Converter
- `com.squareup.okhttp3:logging-interceptor:4.11.0` - Network Logging

### Image Loading
- `com.github.bumptech.glide:glide:4.15.1` - Image loading library

### Material Design
- `com.google.android.material:material:1.10.0` - Material Components

### Database (Optional)
- `androidx.room:room-runtime:2.5.2` - Local Database

## Setup Instructions

### Prerequisites
- Android Studio (Arctic Fox or later)
- JDK 17+
- Android SDK 24+ (Target SDK 34)

### Installation Steps

1. **Open Project**
   ```bash
   # In Android Studio, open the utephonehub folder
   ```

2. **Configure Backend URL**
   - Edit `src/main/java/com/utephonehub/api/ApiService.java`
   - Change `BASE_URL` to your backend URL:
   ```java
   String BASE_URL = "http://your-backend-url:8080/api/v1/";
   ```

3. **Build the Project**
   - Click Build → Make Project
   - Or use terminal: `./gradlew build`

4. **Run the App**
   - Click Run → Run 'app'
   - Select an emulator or connected device

## API Integration

The app connects to the backend API with the following endpoints:

```
GET /api/v1/products?page=1&size=10
GET /api/v1/products/{id}
GET /api/v1/products/search?keyword=phone&page=1&size=10
GET /api/v1/health
GET /api/v1/categories
GET /api/v1/brands
```

## Data Flow

### Loading Products
```
MainActivity
    ↓
ProductViewModel.loadProducts()
    ↓
ProductRepository.getProducts()
    ↓
ApiService.getProducts() (Retrofit)
    ↓
LiveData emits products
    ↓
ProductAdapter displays in RecyclerView
```

### Product Detail
```
ProductDetailActivity
    ↓
ProductViewModel.loadProductDetail()
    ↓
ProductRepository.getProductById()
    ↓
ApiService.getProductById()
    ↓
LiveData emits product
    ↓
UI displays full details
```

## Future Enhancements

1. **Authentication**
   - Implement JWT token management
   - Secure token storage using EncryptedSharedPreferences

2. **Shopping Cart**
   - Local Room database for cart items
   - Sync with server cart

3. **Payment Integration**
   - Stripe/PayPal integration
   - Order creation API

4. **Search & Filter**
   - Advanced filtering by category, brand, price
   - Sort options

5. **Reviews & Ratings**
   - Display product reviews
   - Submit new reviews

6. **Notifications**
   - Push notifications for order updates
   - Firebase Cloud Messaging integration

## Testing

### Unit Tests
```bash
./gradlew test
```

### Instrumentation Tests
```bash
./gradlew connectedAndroidTest
```

## Troubleshooting

### Connection Issues
- Verify backend is running on `http://localhost:8080`
- Check firewall settings
- Ensure app has INTERNET permission

### Image Loading Issues
- Check image URLs are accessible
- Verify Glide configuration
- Check logcat for Glide errors

### API Response Issues
- Check backend API response format
- Verify JSON serialization in models
- Enable network logging in RetrofitClient

## Contributing

1. Create a feature branch
2. Commit changes
3. Push to repository
4. Create a pull request

## License

MIT License

## Contact

For questions or support, contact the development team.
