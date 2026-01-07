# Hướng Dẫn Sử Dụng Font Roboto

## 📝 Tổng Quan

Font **Roboto** đã được tích hợp vào tất cả các trang của UTE Phone Hub. Đây là font mặc định của Google, được thiết kế cho giao diện hiện đại, dễ đọc và chuyên nghiệp.

---

## 🎨 Font Weights Có Sẵn

| Weight | Class Name            | Sử Dụng Cho            |
| ------ | --------------------- | ---------------------- |
| 100    | `.roboto-thin`        | Text rất mỏng, ít dùng |
| 200    | `.roboto-extra-light` | Text nhẹ, decoration   |
| 300    | `.roboto-light`       | Subheadings, captions  |
| 400    | `.roboto-regular`     | Body text (mặc định)   |
| 500    | `.roboto-medium`      | Emphasis, highlights   |
| 600    | `.roboto-semi-bold`   | Subheadings quan trọng |
| 700    | `.roboto-bold`        | Headings, buttons      |
| 800    | `.roboto-extra-bold`  | Titles lớn             |
| 900    | `.roboto-black`       | Headers đặc biệt       |

---

## 💻 Cách Sử Dụng

### 1. **Sử dụng CSS classes (Recommended)**

Thêm link CSS vào trang:

```html
<link
  rel="stylesheet"
  href="${pageContext.request.contextPath}/static/css/components/roboto-font.css"
/>
```

Áp dụng class:

```html
<!-- Headings -->
<h1 class="roboto-bold">Tiêu đề chính</h1>
<h2 class="roboto-semi-bold">Tiêu đề phụ</h2>
<h3 class="roboto-medium">Tiêu đề cấp 3</h3>

<!-- Body text -->
<p class="roboto-regular">Đoạn văn bản thông thường</p>
<p class="roboto-light">Văn bản nhẹ nhàng</p>

<!-- Emphasis -->
<span class="roboto-medium">Văn bản nổi bật</span>
<strong class="roboto-bold">Văn bản đậm</strong>

<!-- Italic -->
<em class="roboto-italic">Văn bản nghiêng</em>
<em class="roboto-bold-italic">Văn bản đậm nghiêng</em>

<!-- Buttons -->
<button class="roboto-medium">Click Me</button>

<!-- Prices/Numbers -->
<span class="roboto-bold">2.990.000₫</span>
```

---

### 2. **Sử dụng trực tiếp trong CSS**

Cho element cụ thể:

```css
.product-title {
  font-family: "Roboto", sans-serif;
  font-weight: 700; /* Bold */
}

.product-price {
  font-family: "Roboto", sans-serif;
  font-weight: 600; /* Semi-bold */
}

.product-description {
  font-family: "Roboto", sans-serif;
  font-weight: 400; /* Regular */
}
```

---

## 🎯 Use Cases Thực Tế

### Trang Sản Phẩm

```html
<div class="product-card">
  <h3 class="roboto-semi-bold">iPhone 15 Pro Max</h3>
  <p class="roboto-light">256GB - Chính hãng VN/A</p>
  <span class="roboto-bold product-price">29.990.000₫</span>
  <span class="roboto-regular old-price">34.990.000₫</span>
  <button class="roboto-medium btn-buy">Mua ngay</button>
</div>
```

### Form Đăng Nhập

```html
<form class="login-form">
  <h2 class="roboto-bold">Đăng nhập</h2>
  <label class="roboto-regular">Email</label>
  <input type="email" placeholder="Nhập email của bạn" />

  <label class="roboto-regular">Mật khẩu</label>
  <input type="password" placeholder="Nhập mật khẩu" />

  <button class="roboto-medium">Đăng nhập</button>
  <a href="#" class="roboto-light">Quên mật khẩu?</a>
</form>
```

### Header Navigation

```html
<nav class="header-nav">
  <a href="/" class="roboto-medium">Trang chủ</a>
  <a href="/products" class="roboto-medium">Sản phẩm</a>
  <a href="/about" class="roboto-medium">Về chúng tôi</a>
  <a href="/contact" class="roboto-medium">Liên hệ</a>
</nav>
```

### Toast Notifications

```html
<div class="toast toast-success">
  <i class="fas fa-check-circle"></i>
  <span class="roboto-medium">Đăng nhập thành công!</span>
</div>

<div class="toast toast-error">
  <i class="fas fa-times-circle"></i>
  <span class="roboto-medium">Email hoặc mật khẩu không đúng!</span>
</div>
```

---

## 📐 Best Practices

### 1. **Hierarchy (Thứ bậc)**

```
H1: roboto-bold (700) - Tiêu đề trang chính
H2: roboto-semi-bold (600) - Tiêu đề section
H3: roboto-medium (500) - Tiêu đề sub-section
Body: roboto-regular (400) - Văn bản thông thường
Caption: roboto-light (300) - Ghi chú, metadata
```

### 2. **Contrast (Độ tương phản)**

- Không dùng `thin (100)` hoặc `extra-light (200)` cho text nhỏ
- Dùng `bold (700)` cho CTA buttons
- Dùng `medium (500)` cho links và emphasis

### 3. **Readability (Dễ đọc)**

- Body text: Luôn dùng `regular (400)` hoặc `light (300)`
- Headings: Dùng `semi-bold (600)` đến `bold (700)`
- Numbers/Prices: Dùng `bold (700)` để nổi bật

### 4. **Performance**

- Font Roboto đã được optimize với variable font
- Tải tất cả weights trong 1 request duy nhất
- Sử dụng `preconnect` để giảm latency

---

## 🔧 Customization

### Tạo class riêng cho brand

```css
/* Trong file custom.css */
.brand-title {
  font-family: "Roboto", sans-serif;
  font-weight: 900; /* Black */
  font-size: 2.5rem;
  color: #ff6b35;
  text-transform: uppercase;
}

.brand-subtitle {
  font-family: "Roboto", sans-serif;
  font-weight: 300; /* Light */
  font-size: 1.2rem;
  color: #666;
  letter-spacing: 0.5px;
}
```

### Override global body font

```css
/* Đã set mặc định trong main.css */
body {
  font-family: "Roboto", "Segoe UI", sans-serif;
  font-weight: 400;
}
```

---

## ✅ Checklist Khi Thêm Trang Mới

- [ ] Add Roboto font links vào `<head>`

```html
<link rel="preconnect" href="https://fonts.googleapis.com" />
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
<link
  href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100..900;1,100..900&display=swap"
  rel="stylesheet"
/>
```

- [ ] (Optional) Link roboto-font.css nếu muốn dùng utility classes

```html
<link
  rel="stylesheet"
  href="${pageContext.request.contextPath}/static/css/components/roboto-font.css"
/>
```

- [ ] Apply font weights phù hợp:
  - H1, H2: `roboto-bold` hoặc `roboto-semi-bold`
  - Body text: `roboto-regular`
  - Buttons, Links: `roboto-medium`
  - Captions: `roboto-light`

---

## 📚 Resources

- [Google Fonts - Roboto](https://fonts.google.com/specimen/Roboto)
- [Roboto Specimen](https://fonts.google.com/specimen/Roboto#standard-styles)
- [Font Pairing Guide](https://www.fontpair.co/fonts/roboto)

---

## 🎨 Color Combinations

Roboto pairs well với color scheme hiện tại:

```css
/* Primary text */
color: #333;
font-family: "Roboto", sans-serif;
font-weight: 400;

/* Headings */
color: #ff6b35; /* Brand color */
font-family: "Roboto", sans-serif;
font-weight: 700;

/* Muted text */
color: #999;
font-family: "Roboto", sans-serif;
font-weight: 300;

/* Buttons */
background: #ff6b35;
color: white;
font-family: "Roboto", sans-serif;
font-weight: 500;
```

---

## 💡 Tips

1. **Don't overuse weights**: Chỉ dùng 3-4 weights trong 1 trang để giữ consistency
2. **Line height matters**: Roboto works best với line-height: 1.5-1.7
3. **Letter spacing**: Thêm `letter-spacing: 0.3px` cho headings lớn
4. **Fallback fonts**: Luôn có fallback: `"Roboto", sans-serif`
5. **Test on mobile**: Check readability trên mobile devices

---

Enjoy coding with Roboto! 🚀
