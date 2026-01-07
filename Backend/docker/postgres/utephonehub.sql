--
-- PostgreSQL database dump
--

\restrict f0U3NkO3gLT2TyDeaO6sxNSujJai0kd8fsYQaERJzs7cditjt0uhbLKK40cad7f

-- Dumped from database version 17.6
-- Dumped by pg_dump version 17.6

-- Started on 2025-10-15 05:46:50

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 4135 (class 1262 OID 5)
-- Name: postgres; Type: DATABASE; Schema: -; Owner: cloudsqlsuperuser
--

CREATE DATABASE postgres WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'en_US.UTF8';


ALTER DATABASE postgres OWNER TO cloudsqlsuperuser;

\unrestrict f0U3NkO3gLT2TyDeaO6sxNSujJai0kd8fsYQaERJzs7cditjt0uhbLKK40cad7f
\connect postgres
\restrict f0U3NkO3gLT2TyDeaO6sxNSujJai0kd8fsYQaERJzs7cditjt0uhbLKK40cad7f

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 4136 (class 0 OID 0)
-- Dependencies: 4135
-- Name: DATABASE postgres; Type: COMMENT; Schema: -; Owner: cloudsqlsuperuser
--

COMMENT ON DATABASE postgres IS 'default administrative connection database';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 220 (class 1259 OID 16829)
-- Name: addresses; Type: TABLE; Schema: public; Owner: utephonehub_user
--

CREATE TABLE public.addresses (
    id bigint NOT NULL,
    user_id bigint NOT NULL,
    recipient_name character varying(100) NOT NULL,
    phone_number character varying(20) NOT NULL,
    street_address text NOT NULL,
    ward character varying(100) NOT NULL,
    ward_code character varying(10),
    province character varying(100) NOT NULL,
    province_code character varying(10),
    is_default boolean DEFAULT false,
    created_at timestamp without time zone,
    updated_at timestamp without time zone
);


ALTER TABLE public.addresses OWNER TO utephonehub_user;

--
-- TOC entry 219 (class 1259 OID 16828)
-- Name: addresses_id_seq; Type: SEQUENCE; Schema: public; Owner: utephonehub_user
--

CREATE SEQUENCE public.addresses_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.addresses_id_seq OWNER TO utephonehub_user;

--
-- TOC entry 4138 (class 0 OID 0)
-- Dependencies: 219
-- Name: addresses_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: utephonehub_user
--

ALTER SEQUENCE public.addresses_id_seq OWNED BY public.addresses.id;


--
-- TOC entry 224 (class 1259 OID 16858)
-- Name: brands; Type: TABLE; Schema: public; Owner: utephonehub_user
--

CREATE TABLE public.brands (
    id bigint NOT NULL,
    name character varying(100) NOT NULL,
    description text,
    logo_url character varying(500),
    created_at timestamp without time zone,
    updated_at timestamp without time zone
);


ALTER TABLE public.brands OWNER TO utephonehub_user;

--
-- TOC entry 223 (class 1259 OID 16857)
-- Name: brands_id_seq; Type: SEQUENCE; Schema: public; Owner: utephonehub_user
--

CREATE SEQUENCE public.brands_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.brands_id_seq OWNER TO utephonehub_user;

--
-- TOC entry 4139 (class 0 OID 0)
-- Dependencies: 223
-- Name: brands_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: utephonehub_user
--

ALTER SEQUENCE public.brands_id_seq OWNED BY public.brands.id;


--
-- TOC entry 238 (class 1259 OID 16976)
-- Name: cart_items; Type: TABLE; Schema: public; Owner: utephonehub_user
--

CREATE TABLE public.cart_items (
    id bigint NOT NULL,
    cart_id bigint NOT NULL,
    product_id bigint NOT NULL,
    quantity integer NOT NULL,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    CONSTRAINT cart_items_quantity_check CHECK ((quantity > 0))
);


ALTER TABLE public.cart_items OWNER TO utephonehub_user;

--
-- TOC entry 237 (class 1259 OID 16975)
-- Name: cart_items_id_seq; Type: SEQUENCE; Schema: public; Owner: utephonehub_user
--

CREATE SEQUENCE public.cart_items_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.cart_items_id_seq OWNER TO utephonehub_user;

--
-- TOC entry 4140 (class 0 OID 0)
-- Dependencies: 237
-- Name: cart_items_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: utephonehub_user
--

ALTER SEQUENCE public.cart_items_id_seq OWNED BY public.cart_items.id;


--
-- TOC entry 236 (class 1259 OID 16962)
-- Name: carts; Type: TABLE; Schema: public; Owner: utephonehub_user
--

CREATE TABLE public.carts (
    id bigint NOT NULL,
    user_id bigint NOT NULL,
    created_at timestamp without time zone,
    updated_at timestamp without time zone
);


ALTER TABLE public.carts OWNER TO utephonehub_user;

--
-- TOC entry 235 (class 1259 OID 16961)
-- Name: carts_id_seq; Type: SEQUENCE; Schema: public; Owner: utephonehub_user
--

CREATE SEQUENCE public.carts_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.carts_id_seq OWNER TO utephonehub_user;

--
-- TOC entry 4141 (class 0 OID 0)
-- Dependencies: 235
-- Name: carts_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: utephonehub_user
--

ALTER SEQUENCE public.carts_id_seq OWNED BY public.carts.id;


--
-- TOC entry 222 (class 1259 OID 16844)
-- Name: categories; Type: TABLE; Schema: public; Owner: utephonehub_user
--

CREATE TABLE public.categories (
    id bigint NOT NULL,
    name character varying(100) NOT NULL,
    description text,
    parent_id bigint,
    created_at timestamp without time zone,
    updated_at timestamp without time zone
);


ALTER TABLE public.categories OWNER TO utephonehub_user;

--
-- TOC entry 221 (class 1259 OID 16843)
-- Name: categories_id_seq; Type: SEQUENCE; Schema: public; Owner: utephonehub_user
--

CREATE SEQUENCE public.categories_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.categories_id_seq OWNER TO utephonehub_user;

--
-- TOC entry 4142 (class 0 OID 0)
-- Dependencies: 221
-- Name: categories_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: utephonehub_user
--

ALTER SEQUENCE public.categories_id_seq OWNED BY public.categories.id;


--
-- TOC entry 234 (class 1259 OID 16943)
-- Name: order_items; Type: TABLE; Schema: public; Owner: utephonehub_user
--

CREATE TABLE public.order_items (
    id bigint NOT NULL,
    order_id bigint NOT NULL,
    product_id bigint NOT NULL,
    quantity integer NOT NULL,
    price numeric(12,2) NOT NULL,
    created_at timestamp without time zone,
    CONSTRAINT order_items_price_check CHECK ((price > (0)::numeric)),
    CONSTRAINT order_items_quantity_check CHECK ((quantity > 0))
);


ALTER TABLE public.order_items OWNER TO utephonehub_user;

--
-- TOC entry 233 (class 1259 OID 16942)
-- Name: order_items_id_seq; Type: SEQUENCE; Schema: public; Owner: utephonehub_user
--

CREATE SEQUENCE public.order_items_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.order_items_id_seq OWNER TO utephonehub_user;

--
-- TOC entry 4143 (class 0 OID 0)
-- Dependencies: 233
-- Name: order_items_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: utephonehub_user
--

ALTER SEQUENCE public.order_items_id_seq OWNED BY public.order_items.id;


--
-- TOC entry 232 (class 1259 OID 16918)
-- Name: orders; Type: TABLE; Schema: public; Owner: utephonehub_user
--

CREATE TABLE public.orders (
    id bigint NOT NULL,
    order_code character varying(50) NOT NULL,
    user_id bigint,
    email character varying(100) NOT NULL,
    recipient_name character varying(100) NOT NULL,
    phone_number character varying(20) NOT NULL,
    street_address text NOT NULL,
    city character varying(100) NOT NULL,
    status character varying(50) DEFAULT 'PENDING'::character varying NOT NULL,
    payment_method character varying(50) NOT NULL,
    total_amount numeric(12,2) NOT NULL,
    voucher_id bigint,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    CONSTRAINT orders_payment_method_check CHECK (((payment_method)::text = ANY ((ARRAY['COD'::character varying, 'BANK_TRANSFER'::character varying, 'STORE_PICKUP'::character varying])::text[]))),
    CONSTRAINT orders_status_check CHECK (((status)::text = ANY ((ARRAY['PENDING'::character varying, 'PROCESSING'::character varying, 'SHIPPED'::character varying, 'DELIVERED'::character varying, 'CANCELLED'::character varying])::text[]))),
    CONSTRAINT orders_total_amount_check CHECK ((total_amount > (0)::numeric))
);


ALTER TABLE public.orders OWNER TO utephonehub_user;

--
-- TOC entry 231 (class 1259 OID 16917)
-- Name: orders_id_seq; Type: SEQUENCE; Schema: public; Owner: utephonehub_user
--

CREATE SEQUENCE public.orders_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.orders_id_seq OWNER TO utephonehub_user;

--
-- TOC entry 4144 (class 0 OID 0)
-- Dependencies: 231
-- Name: orders_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: utephonehub_user
--

ALTER SEQUENCE public.orders_id_seq OWNED BY public.orders.id;


--
-- TOC entry 228 (class 1259 OID 16890)
-- Name: product_images; Type: TABLE; Schema: public; Owner: utephonehub_user
--

CREATE TABLE public.product_images (
    id bigint NOT NULL,
    product_id bigint NOT NULL,
    image_url character varying(500) NOT NULL,
    alt_text character varying(200),
    is_primary boolean DEFAULT false,
    created_at timestamp without time zone
);


ALTER TABLE public.product_images OWNER TO utephonehub_user;

--
-- TOC entry 227 (class 1259 OID 16889)
-- Name: product_images_id_seq; Type: SEQUENCE; Schema: public; Owner: utephonehub_user
--

CREATE SEQUENCE public.product_images_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.product_images_id_seq OWNER TO utephonehub_user;

--
-- TOC entry 4145 (class 0 OID 0)
-- Dependencies: 227
-- Name: product_images_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: utephonehub_user
--

ALTER SEQUENCE public.product_images_id_seq OWNED BY public.product_images.id;


--
-- TOC entry 226 (class 1259 OID 16867)
-- Name: products; Type: TABLE; Schema: public; Owner: utephonehub_user
--

CREATE TABLE public.products (
    id bigint NOT NULL,
    name character varying(200) NOT NULL,
    description text,
    price numeric(12,2) NOT NULL,
    stock_quantity integer DEFAULT 0 NOT NULL,
    thumbnail_url character varying(500),
    specifications jsonb,
    status boolean DEFAULT true NOT NULL,
    category_id bigint NOT NULL,
    brand_id bigint NOT NULL,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    CONSTRAINT products_price_check CHECK ((price > (0)::numeric)),
    CONSTRAINT products_stock_quantity_check CHECK ((stock_quantity >= 0))
);


ALTER TABLE public.products OWNER TO utephonehub_user;

--
-- TOC entry 225 (class 1259 OID 16866)
-- Name: products_id_seq; Type: SEQUENCE; Schema: public; Owner: utephonehub_user
--

CREATE SEQUENCE public.products_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.products_id_seq OWNER TO utephonehub_user;

--
-- TOC entry 4146 (class 0 OID 0)
-- Dependencies: 225
-- Name: products_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: utephonehub_user
--

ALTER SEQUENCE public.products_id_seq OWNED BY public.products.id;


--
-- TOC entry 242 (class 1259 OID 17018)
-- Name: review_likes; Type: TABLE; Schema: public; Owner: utephonehub_user
--

CREATE TABLE public.review_likes (
    id bigint NOT NULL,
    user_id bigint NOT NULL,
    review_id bigint NOT NULL,
    created_at timestamp without time zone
);


ALTER TABLE public.review_likes OWNER TO utephonehub_user;

--
-- TOC entry 241 (class 1259 OID 17017)
-- Name: review_likes_id_seq; Type: SEQUENCE; Schema: public; Owner: utephonehub_user
--

CREATE SEQUENCE public.review_likes_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.review_likes_id_seq OWNER TO utephonehub_user;

--
-- TOC entry 4147 (class 0 OID 0)
-- Dependencies: 241
-- Name: review_likes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: utephonehub_user
--

ALTER SEQUENCE public.review_likes_id_seq OWNED BY public.review_likes.id;


--
-- TOC entry 240 (class 1259 OID 16996)
-- Name: reviews; Type: TABLE; Schema: public; Owner: utephonehub_user
--

CREATE TABLE public.reviews (
    id bigint NOT NULL,
    user_id bigint NOT NULL,
    product_id bigint NOT NULL,
    rating integer NOT NULL,
    comment text,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    CONSTRAINT reviews_rating_check CHECK (((rating >= 1) AND (rating <= 5)))
);


ALTER TABLE public.reviews OWNER TO utephonehub_user;

--
-- TOC entry 239 (class 1259 OID 16995)
-- Name: reviews_id_seq; Type: SEQUENCE; Schema: public; Owner: utephonehub_user
--

CREATE SEQUENCE public.reviews_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.reviews_id_seq OWNER TO utephonehub_user;

--
-- TOC entry 4148 (class 0 OID 0)
-- Dependencies: 239
-- Name: reviews_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: utephonehub_user
--

ALTER SEQUENCE public.reviews_id_seq OWNED BY public.reviews.id;


--
-- TOC entry 218 (class 1259 OID 16812)
-- Name: users; Type: TABLE; Schema: public; Owner: utephonehub_user
--

CREATE TABLE public.users (
    id bigint NOT NULL,
    username character varying(50) NOT NULL,
    full_name character varying(100) NOT NULL,
    email character varying(100) NOT NULL,
    password_hash character varying(255) NOT NULL,
    phone_number character varying(20),
    role character varying(50) DEFAULT 'customer'::character varying NOT NULL,
    status character varying(50) DEFAULT 'active'::character varying NOT NULL,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    CONSTRAINT users_role_check CHECK (((role)::text = ANY ((ARRAY['customer'::character varying, 'admin'::character varying])::text[]))),
    CONSTRAINT users_status_check CHECK (((status)::text = ANY ((ARRAY['active'::character varying, 'locked'::character varying, 'pending'::character varying])::text[])))
);


ALTER TABLE public.users OWNER TO utephonehub_user;

--
-- TOC entry 217 (class 1259 OID 16811)
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: utephonehub_user
--

CREATE SEQUENCE public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.users_id_seq OWNER TO utephonehub_user;

--
-- TOC entry 4149 (class 0 OID 0)
-- Dependencies: 217
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: utephonehub_user
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- TOC entry 230 (class 1259 OID 16905)
-- Name: vouchers; Type: TABLE; Schema: public; Owner: utephonehub_user
--

CREATE TABLE public.vouchers (
    id bigint NOT NULL,
    code character varying(50) NOT NULL,
    discount_type character varying(50) NOT NULL,
    discount_value numeric(12,2) NOT NULL,
    max_usage integer,
    min_order_value numeric(12,2),
    expiry_date timestamp without time zone,
    status character varying(50) DEFAULT 'ACTIVE'::character varying NOT NULL,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    CONSTRAINT vouchers_discount_type_check CHECK (((discount_type)::text = ANY ((ARRAY['PERCENTAGE'::character varying, 'FIXED_AMOUNT'::character varying])::text[]))),
    CONSTRAINT vouchers_discount_value_check CHECK ((discount_value > (0)::numeric)),
    CONSTRAINT vouchers_status_check CHECK (((status)::text = ANY ((ARRAY['ACTIVE'::character varying, 'INACTIVE'::character varying, 'EXPIRED'::character varying])::text[])))
);


ALTER TABLE public.vouchers OWNER TO utephonehub_user;

--
-- TOC entry 229 (class 1259 OID 16904)
-- Name: vouchers_id_seq; Type: SEQUENCE; Schema: public; Owner: utephonehub_user
--

CREATE SEQUENCE public.vouchers_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.vouchers_id_seq OWNER TO utephonehub_user;

--
-- TOC entry 4150 (class 0 OID 0)
-- Dependencies: 229
-- Name: vouchers_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: utephonehub_user
--

ALTER SEQUENCE public.vouchers_id_seq OWNED BY public.vouchers.id;


--
-- TOC entry 3850 (class 2604 OID 16832)
-- Name: addresses id; Type: DEFAULT; Schema: public; Owner: utephonehub_user
--

ALTER TABLE ONLY public.addresses ALTER COLUMN id SET DEFAULT nextval('public.addresses_id_seq'::regclass);


--
-- TOC entry 3853 (class 2604 OID 16861)
-- Name: brands id; Type: DEFAULT; Schema: public; Owner: utephonehub_user
--

ALTER TABLE ONLY public.brands ALTER COLUMN id SET DEFAULT nextval('public.brands_id_seq'::regclass);


--
-- TOC entry 3865 (class 2604 OID 16979)
-- Name: cart_items id; Type: DEFAULT; Schema: public; Owner: utephonehub_user
--

ALTER TABLE ONLY public.cart_items ALTER COLUMN id SET DEFAULT nextval('public.cart_items_id_seq'::regclass);


--
-- TOC entry 3864 (class 2604 OID 16965)
-- Name: carts id; Type: DEFAULT; Schema: public; Owner: utephonehub_user
--

ALTER TABLE ONLY public.carts ALTER COLUMN id SET DEFAULT nextval('public.carts_id_seq'::regclass);


--
-- TOC entry 3852 (class 2604 OID 16847)
-- Name: categories id; Type: DEFAULT; Schema: public; Owner: utephonehub_user
--

ALTER TABLE ONLY public.categories ALTER COLUMN id SET DEFAULT nextval('public.categories_id_seq'::regclass);


--
-- TOC entry 3863 (class 2604 OID 16946)
-- Name: order_items id; Type: DEFAULT; Schema: public; Owner: utephonehub_user
--

ALTER TABLE ONLY public.order_items ALTER COLUMN id SET DEFAULT nextval('public.order_items_id_seq'::regclass);


--
-- TOC entry 3861 (class 2604 OID 16921)
-- Name: orders id; Type: DEFAULT; Schema: public; Owner: utephonehub_user
--

ALTER TABLE ONLY public.orders ALTER COLUMN id SET DEFAULT nextval('public.orders_id_seq'::regclass);


--
-- TOC entry 3857 (class 2604 OID 16893)
-- Name: product_images id; Type: DEFAULT; Schema: public; Owner: utephonehub_user
--

ALTER TABLE ONLY public.product_images ALTER COLUMN id SET DEFAULT nextval('public.product_images_id_seq'::regclass);


--
-- TOC entry 3854 (class 2604 OID 16870)
-- Name: products id; Type: DEFAULT; Schema: public; Owner: utephonehub_user
--

ALTER TABLE ONLY public.products ALTER COLUMN id SET DEFAULT nextval('public.products_id_seq'::regclass);


--
-- TOC entry 3867 (class 2604 OID 17021)
-- Name: review_likes id; Type: DEFAULT; Schema: public; Owner: utephonehub_user
--

ALTER TABLE ONLY public.review_likes ALTER COLUMN id SET DEFAULT nextval('public.review_likes_id_seq'::regclass);


--
-- TOC entry 3866 (class 2604 OID 16999)
-- Name: reviews id; Type: DEFAULT; Schema: public; Owner: utephonehub_user
--

ALTER TABLE ONLY public.reviews ALTER COLUMN id SET DEFAULT nextval('public.reviews_id_seq'::regclass);


--
-- TOC entry 3847 (class 2604 OID 16815)
-- Name: users id; Type: DEFAULT; Schema: public; Owner: utephonehub_user
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- TOC entry 3859 (class 2604 OID 16908)
-- Name: vouchers id; Type: DEFAULT; Schema: public; Owner: utephonehub_user
--

ALTER TABLE ONLY public.vouchers ALTER COLUMN id SET DEFAULT nextval('public.vouchers_id_seq'::regclass);


--
-- TOC entry 4107 (class 0 OID 16829)
-- Dependencies: 220
-- Data for Name: addresses; Type: TABLE DATA; Schema: public; Owner: utephonehub_user
--

INSERT INTO public.addresses VALUES (10, 6, 'Tuan Tu', '0399963463', '27 Loc Yen', 'Xã Krông Năng', '24343', 'Đắk Lắk', '66', true, '2025-10-13 02:57:57.192243', '2025-10-13 02:57:57.192273');
INSERT INTO public.addresses VALUES (11, 8, 'ute 3', '0399963463', '20 duong so 19 HCM', 'Phường Tân Giang', '1288', 'Cao Bằng', '4', true, '2025-10-13 03:00:12.177113', '2025-10-13 03:00:12.177129');
INSERT INTO public.addresses VALUES (12, 9, 'khang', '0762751676', 'dak lak', 'Phường Hà Giang 1', '694', 'Tuyên Quang', '8', true, '2025-10-13 03:15:38.72946', '2025-10-13 03:15:38.729465');
INSERT INTO public.addresses VALUES (13, 10, 'khang', '0762751676', 'dak lak', 'Phường Hà Giang 2', '691', 'Tuyên Quang', '8', true, '2025-10-13 03:24:56.644077', '2025-10-13 03:24:56.644078');
INSERT INTO public.addresses VALUES (14, 11, 'Quang Duy', '0347903380', '43 street 7, ward Hiep Binh Phuoc, Thu Duc City', 'Phường Bình Thạnh', '26929', 'Thành phố Hồ Chí Minh', '79', true, '2025-10-13 04:02:23.186527', '2025-10-13 04:02:23.186527');
INSERT INTO public.addresses VALUES (16, 17, 'Đỗ Kiến Hung', '0357554576', '01 Võ Văn Ngân', 'Phường Thủ Đức', '26824', 'Thành phố Hồ Chí Minh', '79', true, '2025-10-13 15:45:06.289063', '2025-10-13 15:45:06.289064');


--
-- TOC entry 4111 (class 0 OID 16858)
-- Dependencies: 224
-- Data for Name: brands; Type: TABLE DATA; Schema: public; Owner: utephonehub_user
--

INSERT INTO public.brands VALUES (1, 'Apple', 'Thương hiệu công nghệ hàng đầu từ Mỹ', 'https://upload.wikimedia.org/wikipedia/commons/f/fa/Apple_logo_black.svg', '2025-10-12 23:28:01.554872', '2025-10-12 23:28:01.554872');
INSERT INTO public.brands VALUES (2, 'Samsung', 'Tập đoàn điện tử hàng đầu Hàn Quốc', 'https://upload.wikimedia.org/wikipedia/commons/2/24/Samsung_Logo.svg', '2025-10-12 23:28:01.554872', '2025-10-12 23:28:01.554872');
INSERT INTO public.brands VALUES (3, 'Xiaomi', 'Thương hiệu công nghệ từ Trung Quốc', 'https://upload.wikimedia.org/wikipedia/commons/2/29/Xiaomi_logo.svg', '2025-10-12 23:28:01.554872', '2025-10-12 23:28:01.554872');
INSERT INTO public.brands VALUES (4, 'OPPO', 'Thương hiệu điện thoại thông minh', 'https://upload.wikimedia.org/wikipedia/commons/8/89/Oppo_Logo.svg', '2025-10-12 23:28:01.554872', '2025-10-12 23:28:01.554872');
INSERT INTO public.brands VALUES (5, 'Vivo', 'Thương hiệu điện thoại phổ biến', 'https://upload.wikimedia.org/wikipedia/commons/3/33/Vivo_logo.svg', '2025-10-12 23:28:01.554872', '2025-10-12 23:28:01.554872');
INSERT INTO public.brands VALUES (6, 'Realme', 'Thương hiệu smartphone giá rẻ', NULL, '2025-10-12 23:28:01.554872', '2025-10-12 23:28:01.554872');
INSERT INTO public.brands VALUES (7, 'Huawei', 'Tập đoàn công nghệ Trung Quốc', NULL, '2025-10-12 23:28:01.554872', '2025-10-12 23:28:01.554872');
INSERT INTO public.brands VALUES (9, 'Sony', 'Tập đoàn công nghệ Nhật Bản', NULL, '2025-10-12 23:28:01.554872', '2025-10-12 23:28:01.554872');
INSERT INTO public.brands VALUES (10, 'Asus', 'Thương hiệu máy tính và di động', NULL, '2025-10-12 23:28:01.554872', '2025-10-12 23:28:01.554872');
INSERT INTO public.brands VALUES (11, 'Hp', '', '', '2025-10-13 02:08:03.072769', '2025-10-13 02:08:03.07277');
INSERT INTO public.brands VALUES (12, 'Lenovo', '', '', '2025-10-13 02:12:31.353402', '2025-10-13 02:12:31.353404');
INSERT INTO public.brands VALUES (13, 'Dell', '', '', '2025-10-13 02:12:46.720163', '2025-10-13 02:12:46.720164');
INSERT INTO public.brands VALUES (14, 'MSI', '', '', '2025-10-13 04:41:59.71943', '2025-10-13 04:41:59.719431');
INSERT INTO public.brands VALUES (15, 'Acer', '', '', '2025-10-13 04:42:04.147177', '2025-10-13 04:42:04.147177');
INSERT INTO public.brands VALUES (16, 'Logitech', '', '', '2025-10-13 05:28:51.143047', '2025-10-13 05:28:51.143048');
INSERT INTO public.brands VALUES (17, 'Akko', '', '', '2025-10-13 05:29:51.71752', '2025-10-13 05:29:51.717522');


--
-- TOC entry 4125 (class 0 OID 16976)
-- Dependencies: 238
-- Data for Name: cart_items; Type: TABLE DATA; Schema: public; Owner: utephonehub_user
--

INSERT INTO public.cart_items VALUES (3, 25, 68, 1, '2025-10-13 02:48:09.659855', '2025-10-13 02:48:09.659856');
INSERT INTO public.cart_items VALUES (38, 98, 62, 1, '2025-10-14 02:05:38.339898', '2025-10-14 02:08:02.398859');
INSERT INTO public.cart_items VALUES (39, 98, 61, 1, '2025-10-14 02:14:42.961475', '2025-10-14 02:14:42.961477');
INSERT INTO public.cart_items VALUES (7, 34, 61, 2, '2025-10-13 03:07:47.418826', '2025-10-13 03:10:15.15863');
INSERT INTO public.cart_items VALUES (12, 91, 89, 1, '2025-10-13 03:29:04.125513', '2025-10-13 03:29:04.125515');
INSERT INTO public.cart_items VALUES (27, 24, 61, 4, '2025-10-13 16:25:02.082559', '2025-10-14 15:07:36.569957');
INSERT INTO public.cart_items VALUES (17, 92, 74, 1, '2025-10-13 04:14:33.172465', '2025-10-13 04:14:33.17247');
INSERT INTO public.cart_items VALUES (18, 24, 126, 1, '2025-10-13 10:39:26.739034', '2025-10-13 10:39:26.739035');
INSERT INTO public.cart_items VALUES (25, 24, 63, 1, '2025-10-13 14:43:37.046187', '2025-10-13 14:43:37.046188');
INSERT INTO public.cart_items VALUES (26, 24, 62, 1, '2025-10-13 14:43:38.155378', '2025-10-13 14:43:38.15538');
INSERT INTO public.cart_items VALUES (28, 94, 99, 1, '2025-10-13 16:45:33.934301', '2025-10-13 16:45:33.934302');
INSERT INTO public.cart_items VALUES (32, 101, 62, 1, '2025-10-14 01:40:46.971498', '2025-10-14 01:40:46.971499');
INSERT INTO public.cart_items VALUES (34, 101, 61, 1, '2025-10-14 01:42:18.970247', '2025-10-14 01:42:18.970249');
INSERT INTO public.cart_items VALUES (35, 102, 98, 9, '2025-10-14 01:43:29.762117', '2025-10-14 01:43:54.151733');


--
-- TOC entry 4123 (class 0 OID 16962)
-- Dependencies: 236
-- Data for Name: carts; Type: TABLE DATA; Schema: public; Owner: utephonehub_user
--

INSERT INTO public.carts VALUES (23, 6, '2025-10-13 01:09:46.154007', '2025-10-13 01:09:46.154008');
INSERT INTO public.carts VALUES (24, 1, '2025-10-13 01:16:24.142561', '2025-10-13 01:16:24.142561');
INSERT INTO public.carts VALUES (25, 7, '2025-10-13 02:47:36.638499', '2025-10-13 02:47:36.638499');
INSERT INTO public.carts VALUES (90, 9, '2025-10-13 03:07:41.334929', '2025-10-13 03:07:41.33493');
INSERT INTO public.carts VALUES (34, 8, '2025-10-13 03:00:01.924336', '2025-10-13 03:00:01.924341');
INSERT INTO public.carts VALUES (91, 10, '2025-10-13 03:20:52.590334', '2025-10-13 03:20:52.590334');
INSERT INTO public.carts VALUES (92, 11, '2025-10-13 04:01:37.849517', '2025-10-13 04:01:37.849518');
INSERT INTO public.carts VALUES (93, 12, '2025-10-13 09:09:18.644871', '2025-10-13 09:09:18.644871');
INSERT INTO public.carts VALUES (94, 13, '2025-10-13 11:59:34.762297', '2025-10-13 11:59:34.762297');
INSERT INTO public.carts VALUES (95, 14, '2025-10-13 13:42:55.594434', '2025-10-13 13:42:55.594434');
INSERT INTO public.carts VALUES (96, 16, '2025-10-13 14:12:27.168192', '2025-10-13 14:12:27.168192');
INSERT INTO public.carts VALUES (97, 17, '2025-10-13 14:42:23.610379', '2025-10-13 14:42:23.610379');
INSERT INTO public.carts VALUES (98, 18, '2025-10-13 16:50:08.222188', '2025-10-13 16:50:08.222189');
INSERT INTO public.carts VALUES (99, 19, '2025-10-13 17:10:19.282712', '2025-10-13 17:10:19.282712');
INSERT INTO public.carts VALUES (100, 21, '2025-10-14 01:07:20.924693', '2025-10-14 01:07:20.924693');
INSERT INTO public.carts VALUES (101, 22, '2025-10-14 01:40:41.129473', '2025-10-14 01:40:41.129473');
INSERT INTO public.carts VALUES (102, 15, '2025-10-14 01:41:05.325769', '2025-10-14 01:41:05.32577');
INSERT INTO public.carts VALUES (103, 23, '2025-10-14 01:42:05.332578', '2025-10-14 01:42:05.332579');
INSERT INTO public.carts VALUES (104, 24, '2025-10-14 02:05:12.58728', '2025-10-14 02:05:12.58728');
INSERT INTO public.carts VALUES (105, 25, '2025-10-14 04:02:49.237723', '2025-10-14 04:02:49.237724');


--
-- TOC entry 4109 (class 0 OID 16844)
-- Dependencies: 222
-- Data for Name: categories; Type: TABLE DATA; Schema: public; Owner: utephonehub_user
--

INSERT INTO public.categories VALUES (1, 'Điện thoại', 'Các loại điện thoại thông minh', NULL, '2025-10-12 23:28:01.513151', '2025-10-12 23:28:01.513151');
INSERT INTO public.categories VALUES (2, 'Laptop', '', NULL, '2025-10-13 00:39:41.942747', '2025-10-13 00:39:41.942748');
INSERT INTO public.categories VALUES (3, 'Phụ kiện', '', NULL, '2025-10-13 00:41:20.303487', '2025-10-13 00:41:20.303488');
INSERT INTO public.categories VALUES (4, 'Smartwatch', '', NULL, '2025-10-13 00:41:39.486486', '2025-10-13 00:41:39.486487');
INSERT INTO public.categories VALUES (5, 'Tablet', '', NULL, '2025-10-13 00:41:46.100602', '2025-10-13 00:41:46.100603');
INSERT INTO public.categories VALUES (6, 'Máy cũ', '', NULL, '2025-10-13 00:41:52.85825', '2025-10-13 00:41:52.858251');
INSERT INTO public.categories VALUES (7, 'Màn hình', '', NULL, '2025-10-13 00:42:00.35275', '2025-10-13 00:42:00.352751');


--
-- TOC entry 4121 (class 0 OID 16943)
-- Dependencies: 234
-- Data for Name: order_items; Type: TABLE DATA; Schema: public; Owner: utephonehub_user
--

INSERT INTO public.order_items VALUES (1, 1, 61, 1, 29990000.00, '2025-10-13 02:49:53.126679');
INSERT INTO public.order_items VALUES (2, 2, 61, 1, 29990000.00, '2025-10-13 03:13:19.879254');
INSERT INTO public.order_items VALUES (3, 2, 62, 1, 27990000.00, '2025-10-13 03:13:19.904609');
INSERT INTO public.order_items VALUES (4, 3, 65, 1, 9990000.00, '2025-10-13 03:16:01.279816');
INSERT INTO public.order_items VALUES (5, 4, 91, 1, 19990000.00, '2025-10-13 03:18:12.150608');
INSERT INTO public.order_items VALUES (6, 5, 61, 1, 29990000.00, '2025-10-13 03:27:55.802947');
INSERT INTO public.order_items VALUES (7, 6, 61, 1, 29990000.00, '2025-10-13 03:29:17.101331');
INSERT INTO public.order_items VALUES (8, 7, 62, 1, 27990000.00, '2025-10-13 03:59:35.607811');
INSERT INTO public.order_items VALUES (9, 8, 61, 3, 29990000.00, '2025-10-13 04:04:37.30377');
INSERT INTO public.order_items VALUES (10, 9, 64, 15, 22990000.00, '2025-10-13 12:02:35.901147');
INSERT INTO public.order_items VALUES (11, 10, 63, 1, 24990000.00, '2025-10-13 13:47:14.992506');
INSERT INTO public.order_items VALUES (12, 10, 62, 2, 27990000.00, '2025-10-13 13:47:15.011995');
INSERT INTO public.order_items VALUES (13, 11, 62, 1, 27990000.00, '2025-10-13 15:45:47.63216');
INSERT INTO public.order_items VALUES (14, 11, 61, 1, 29990000.00, '2025-10-13 15:45:47.653559');
INSERT INTO public.order_items VALUES (15, 11, 63, 1, 24990000.00, '2025-10-13 15:45:47.670646');
INSERT INTO public.order_items VALUES (16, 12, 61, 6, 29990000.00, '2025-10-14 01:42:27.857407');
INSERT INTO public.order_items VALUES (17, 13, 62, 1, 27990000.00, '2025-10-14 01:42:32.631483');
INSERT INTO public.order_items VALUES (18, 13, 61, 2, 29990000.00, '2025-10-14 01:42:32.650509');
INSERT INTO public.order_items VALUES (19, 14, 61, 1, 29990000.00, '2025-10-14 04:03:32.138618');
INSERT INTO public.order_items VALUES (20, 15, 95, 1, 11990000.00, '2025-10-14 13:58:59.083511');
INSERT INTO public.order_items VALUES (21, 15, 61, 1, 29990000.00, '2025-10-14 13:58:59.138201');
INSERT INTO public.order_items VALUES (22, 16, 112, 1, 34990000.00, '2025-10-14 14:36:26.666587');


--
-- TOC entry 4119 (class 0 OID 16918)
-- Dependencies: 232
-- Data for Name: orders; Type: TABLE DATA; Schema: public; Owner: utephonehub_user
--

INSERT INTO public.orders VALUES (1, 'UTEHUB-1760323793', 1, 'fansjaki@gmail.com', 'Nguyễn Văn Quang Duy', '0347903380', '43 street 7, ward Hiep Binh Phuoc, Thu Duc City', 'Thành phố Hồ Chí Minh', 'PROCESSING', 'COD', 29990000.00, NULL, '2025-10-13 02:49:53.106571', '2025-10-13 03:05:58.782237');
INSERT INTO public.orders VALUES (13, 'UTEHUB-1760406152', 17, 'kienhung.do1105@gmail.com', 'Đỗ Kiến Hung', '0357554576', '01 Võ Văn Ngân', 'Thành phố Hồ Chí Minh', 'PENDING', 'COD', 74774500.00, 3, '2025-10-14 01:42:32.571056', '2025-10-14 01:42:32.682099');
INSERT INTO public.orders VALUES (4, 'UTEHUB-1760325492', 6, 'ttgaming1999@gmail.com', 'Tuan Tu', '0399963463', '27 Loc Yen', 'Đắk Lắk', 'PENDING', 'COD', 19990000.00, NULL, '2025-10-13 03:18:12.08876', '2025-10-13 03:18:12.18599');
INSERT INTO public.orders VALUES (12, 'UTEHUB-1760406147', 15, 'trinhnguyen11012005@gmail.com', 'Trinh Van Nguyen', '0862117920', 'AAAAAAAAAAAAAAAAA', 'Quảng Ninh', 'DELIVERED', 'STORE_PICKUP', 179940000.00, NULL, '2025-10-14 01:42:27.839387', '2025-10-14 01:45:23.022809');
INSERT INTO public.orders VALUES (5, 'UTEHUB-1760326075', 10, 'giakhang0167@gmail.com', 'khang', '0762751676', 'dak lak', 'Tuyên Quang', 'DELIVERED', 'COD', 26991000.00, 1, '2025-10-13 03:27:55.74537', '2025-10-13 03:35:45.300812');
INSERT INTO public.orders VALUES (2, 'UTEHUB-1760325199', 9, '22110347@st.hcmute.edu.vn', 'khang', '0762751676', 'dak lak', 'Sơn La', 'SHIPPED', 'COD', 57980000.00, NULL, '2025-10-13 03:13:19.851135', '2025-10-13 03:36:01.518221');
INSERT INTO public.orders VALUES (3, 'UTEHUB-1760325361', 9, 'nguyengiakhang116@gmail.com', 'khang', '0762751676', 'dak lak', 'Tuyên Quang', 'PROCESSING', 'COD', 9990000.00, NULL, '2025-10-13 03:16:01.224073', '2025-10-13 03:36:06.432429');
INSERT INTO public.orders VALUES (8, 'UTEHUB-1760328277', 11, 'fansjaki@gmail.com', 'Quang Duy', '0347903380', '43 street 7, ward Hiep Binh Phuoc, Thu Duc City', 'Thành phố Hồ Chí Minh', 'DELIVERED', 'COD', 89970000.00, NULL, '2025-10-13 04:04:37.232983', '2025-10-13 04:10:44.148258');
INSERT INTO public.orders VALUES (7, 'UTEHUB-1760327974', 6, 'ttgaming1999@gmail.com', 'Tuan Tu', '0399963463', '27 Loc Yen', 'Đắk Lắk', 'PROCESSING', 'COD', 27990000.00, NULL, '2025-10-13 03:59:34.794934', '2025-10-13 04:18:24.282425');
INSERT INTO public.orders VALUES (6, 'UTEHUB-1760326157', 6, 'ttgaming1999@gmail.com', 'Tuan Tu', '0399963463', '27 Loc Yen', 'Đắk Lắk', 'DELIVERED', 'COD', 29990000.00, NULL, '2025-10-13 03:29:17.045914', '2025-10-13 04:18:38.983102');
INSERT INTO public.orders VALUES (9, 'UTEHUB-1760356955', 13, 'tranqus@gmai', 'Trần Quốc Giang', '0858587454', '122', 'Phú Thọ', 'PENDING', 'STORE_PICKUP', 344850000.00, NULL, '2025-10-13 12:02:35.875547', '2025-10-13 12:02:35.969067');
INSERT INTO public.orders VALUES (10, 'UTEHUB-1760363234', 14, '23133019@gmail.com', 'Nguyễn Lê Hoàng Kiệt', '0362324302', 'Biên Hòa', 'Hưng Yên', 'PENDING', 'COD', 80970000.00, NULL, '2025-10-13 13:47:14.976865', '2025-10-13 13:47:15.05161');
INSERT INTO public.orders VALUES (11, 'UTEHUB-1760370347', 17, 'kienhung.do1105@gmail.com', 'Đỗ Kiến Hung', '0357554576', '01 Võ Văn Ngân', 'Thành phố Hồ Chí Minh', 'PENDING', 'COD', 70524500.00, 3, '2025-10-13 15:45:47.574439', '2025-10-13 15:45:47.714343');
INSERT INTO public.orders VALUES (14, 'UTEHUB-1760414612', 25, '23162056@student.hcmute.edu.vn', 'Văn Đức Công Minh', '0935959980', '123', 'Thành phố Hồ Chí Minh', 'DELIVERED', 'COD', 29990000.00, NULL, '2025-10-14 04:03:32.122853', '2025-10-14 04:23:41.837331');
INSERT INTO public.orders VALUES (15, 'UTEHUB-1760450338', 17, 'kienhung.do1105@gmail.com', 'Đỗ Kiến Hung', '0357554576', '01 Võ Văn Ngân', 'Thành phố Hồ Chí Minh', 'DELIVERED', 'COD', 35683000.00, 3, '2025-10-14 13:58:58.96172', '2025-10-14 14:02:39.067037');
INSERT INTO public.orders VALUES (16, 'UTEHUB-1760452586', 17, 'kienhung.do1105@gmail.com', 'Đỗ Kiến Hung', '0357554576', '01 Võ Văn Ngân', 'Thành phố Hồ Chí Minh', 'DELIVERED', 'COD', 29741500.00, 3, '2025-10-14 14:36:26.561689', '2025-10-14 14:40:15.167229');


--
-- TOC entry 4115 (class 0 OID 16890)
-- Dependencies: 228
-- Data for Name: product_images; Type: TABLE DATA; Schema: public; Owner: utephonehub_user
--



--
-- TOC entry 4113 (class 0 OID 16867)
-- Dependencies: 226
-- Data for Name: products; Type: TABLE DATA; Schema: public; Owner: utephonehub_user
--

INSERT INTO public.products VALUES (1, 'iPhone 15 Pro Max 256GB', 'iPhone 15 Pro Max với chip A17 Pro mạnh mẽ nhất, camera 48MP chuyên nghiệp, khung Titan cao cấp, màn hình Super Retina XDR 6.7 inch với ProMotion 120Hz. Pin trâu, sạc nhanh, hỗ trợ 5G.', 32990000.00, 50, 'https://cdn.tgdd.vn/Products/Images/42/305658/iphone-15-pro-max-blue-1-1.jpg', '{"os": "iOS 17", "ram": "8GB", "chip": "A17 Pro", "camera": "48MP", "screen": "6.7 inch", "battery": "4422mAh", "storage": "256GB"}', true, 1, 1, '2025-10-12 23:28:01.599697', '2025-10-12 23:28:01.599697');
INSERT INTO public.products VALUES (64, 'OPPO Find N3 Flip 5G 12GB 256GB', 'OPPO Find N3 Flip - Điện thoại gập vỏ sò, Camera Hasselblad 50MP', 22990000.00, 0, 'https://cdn2.cellphones.com.vn/insecure/rs:fill:358:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/o/p/oppo-find-n3-flip.png', '{"os": "Android 13, ColorOS 13.2", "cpu": "MediaTek Dimensity 9200", "ram": "12GB", "color": ["Đen", "Vàng Đồng"], "camera": "Camera chính: 50MP Hasselblad, 48MP, 32MP", "screen": "6.8 inch chính + 3.26 inch phụ, AMOLED", "weight": "198g", "battery": "4300mAh, Sạc nhanh 44W", "storage": "256GB", "camera_front": "32MP"}', true, 1, 4, NULL, '2025-10-13 12:02:35.922637');
INSERT INTO public.products VALUES (7, 'Samsung Galaxy S23 Ultra 8GB/256GB', 'Galaxy S23 Ultra camera 200MP, hiệu năng đỉnh cao với Snapdragon 8 Gen 2, màn hình 6.8 inch QHD+, S Pen tích hợp.', 25990000.00, 35, 'https://cdn.tgdd.vn/Products/Images/42/249948/samsung-galaxy-s23-ultra-1-1.jpg', '{"os": "Android 13", "ram": "8GB", "chip": "Snapdragon 8 Gen 2", "camera": "200MP", "screen": "6.8 inch", "battery": "5000mAh", "storage": "256GB"}', true, 1, 2, '2025-10-12 23:28:01.645129', '2025-10-12 23:28:01.645129');
INSERT INTO public.products VALUES (67, 'Samsung Galaxy A55 5G 8GB 128GB', 'Galaxy A55 5G - Thiết kế kim loại cao cấp, Camera 50MP', 9490000.00, 40, 'https://cdn2.cellphones.com.vn/insecure/rs:fill:358:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/s/a/samsung-galaxy-a55.png', '{"os": "Android 14, One UI 6.1", "cpu": "Exynos 1480", "ram": "8GB", "color": ["Xanh Bạc Hà", "Tím Mộng Mơ", "Xanh Hải Quân"], "camera": "Camera chính: 50MP, 12MP, 5MP", "screen": "6.6 inch, Super AMOLED", "weight": "213g", "battery": "5000mAh, Sạc nhanh 25W", "storage": "128GB", "camera_front": "32MP"}', true, 1, 2, NULL, NULL);
INSERT INTO public.products VALUES (63, 'Xiaomi 14 Ultra 16GB 512GB', 'Xiaomi 14 Ultra - Camera Leica 50MP, Snapdragon 8 Gen 3, Sạc nhanh 90W', 24990000.00, 18, 'https://cdn2.cellphones.com.vn/358x/media/catalog/product/x/i/xiaomi-14-ultra-2.jpg', '{"os": "Android 14, HyperOS", "cpu": "Snapdragon 8 Gen 3", "ram": "16GB", "color": ["Đen", "Trắng"], "camera": "Camera chính: 50MP Leica, 50MP, 50MP, 50MP", "screen": "6.73 inch, AMOLED", "weight": "224g", "battery": "5000mAh, Sạc nhanh 90W", "storage": "512GB", "camera_front": "32MP"}', true, 1, 3, NULL, '2025-10-13 15:45:47.679495');
INSERT INTO public.products VALUES (61, 'iPhone 15 Pro Max 256GB', 'iPhone 15 Pro Max - Titan mạnh mẽ, Camera tiên tiến, A17 Pro chip', 29990000.00, 7, 'https://cdn2.cellphones.com.vn/insecure/rs:fill:358:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/i/p/iphone-15-pro-max_3.png', '{"os": "iOS 17", "cpu": "Apple A17 Pro 6 nhân", "ram": "8GB", "color": ["Titan Tự Nhiên", "Titan Xanh", "Titan Trắng", "Titan Đen"], "camera": "Camera chính: 48MP, 12MP, 12MP", "screen": "6.7 inch, Super Retina XDR OLED", "weight": "221g", "battery": "4422mAh, Sạc nhanh 27W", "storage": "256GB", "camera_front": "12MP"}', true, 1, 1, NULL, '2025-10-14 13:58:59.171388');
INSERT INTO public.products VALUES (66, 'Realme 12 Pro+ 5G 12GB 256GB', 'Realme 12 Pro+ - Camera tele 3x, Snapdragon 7s Gen 2', 10990000.00, 28, 'https://dienthoaihay.vn/images/products/2024/02/29/large/realme-12-pro-plus-vang_1709190298.jpg', '{"os": "Android 14, Realme UI 5.0", "cpu": "Snapdragon 7s Gen 2", "ram": "12GB", "color": ["Xanh Ngọc", "Kem"], "camera": "Camera chính: 50MP, 64MP tele 3x, 8MP", "screen": "6.7 inch, AMOLED", "weight": "196g", "battery": "5000mAh, Sạc nhanh 67W", "storage": "256GB", "camera_front": "32MP"}', true, 1, 6, NULL, '2025-10-13 02:13:46.900036');
INSERT INTO public.products VALUES (69, 'Dell XPS 13 Plus 9320 i7 32GB 1TB', 'Dell XPS 13 Plus - Thiết kế cao cấp, Intel Core i7 Gen 12, Màn hình OLED', 42990000.00, 12, 'https://thailongcomputer.com/wp-content/uploads/2023/09/3-laptop-dell-xps-13-plus-9320-p.jpg', '{"os": "Windows 11 Pro", "cpu": "Intel Core i7-1260P", "gpu": "Intel Iris Xe Graphics", "ram": "32GB LPDDR5", "color": ["Graphite"], "ports": ["2x Thunderbolt 4"], "screen": "13.4 inch, OLED 3.5K", "weight": "1.24 kg", "battery": "55Wh", "storage": "1TB SSD"}', true, 2, 13, NULL, '2025-10-13 02:15:32.212514');
INSERT INTO public.products VALUES (71, 'HP Envy 13 i7 16GB 512GB', 'HP Envy 13 - Laptop văn phòng cao cấp, Thiết kế sang trọng', 24990000.00, 15, 'https://cdn2.cellphones.com.vn/x/media/catalog/product/l/a/laptop_hp_envy_13-ba0046tu_171m7pa_0001_layer_3.jpg', '{"os": "Windows 11 Home", "cpu": "Intel Core i7-1355U", "gpu": "Intel Iris Xe Graphics", "ram": "16GB DDR4", "color": ["Natural Silver"], "ports": ["2x Thunderbolt 4", "USB-A", "HDMI"], "screen": "13.3 inch, FHD IPS", "weight": "1.3 kg", "battery": "51Wh", "storage": "512GB SSD"}', true, 2, 11, NULL, '2025-10-13 02:16:00.973191');
INSERT INTO public.products VALUES (65, 'Vivo V30e 12GB 256GB', 'Vivo V30e - Camera chân dung Aura Light, Snapdragon 6 Gen 1', 9990000.00, 34, 'https://cdn2.cellphones.com.vn/358x/media/catalog/product/d/i/dien-thoai-vivo-v30e_2__1.png', '{"os": "Android 14, Funtouch OS 14", "cpu": "Snapdragon 6 Gen 1", "ram": "12GB", "color": ["Xanh Lá", "Tím"], "camera": "Camera chính: 50MP, 8MP", "screen": "6.78 inch, AMOLED", "weight": "188g", "battery": "5500mAh, Sạc nhanh 44W", "storage": "256GB", "camera_front": "32MP"}', true, 1, 5, NULL, '2025-10-13 03:16:01.29298');
INSERT INTO public.products VALUES (74, 'AirPods Pro Gen 2 USB-C', 'AirPods Pro 2 - Chống ồn chủ động, Chip H2, Sạc USB-C', 5990000.00, 50, 'https://cdn2.cellphones.com.vn/358x/media/catalog/product/a/p/apple-airpods-pro-2-usb-c_8__1.png', '{"anc": "Chống ồn chủ động", "type": "True Wireless", "color": ["Trắng"], "battery": "Tai nghe: 6 giờ, Hộp sạc: 30 giờ", "charging": "USB-C, MagSafe, Qi", "features": ["Spatial Audio", "Adaptive Audio", "IPX4"], "connectivity": "Bluetooth 5.3"}', true, 3, 1, NULL, '2025-10-13 02:18:13.146607');
INSERT INTO public.products VALUES (75, 'Samsung Galaxy Buds2 Pro', 'Galaxy Buds2 Pro - Chống ồn thông minh, Âm thanh 360', 3490000.00, 45, 'https://cdn2.cellphones.com.vn/358x/media/catalog/product/b/u/busd2-den.jpg', '{"anc": "Intelligent ANC", "type": "True Wireless", "color": ["Tím", "Đen", "Trắng"], "battery": "Tai nghe: 5 giờ, Hộp sạc: 18 giờ", "charging": "USB-C, Qi Wireless", "features": ["360 Audio", "Hi-Fi 24bit", "IPX7"], "connectivity": "Bluetooth 5.3"}', true, 3, 2, NULL, '2025-10-13 02:18:39.83845');
INSERT INTO public.products VALUES (79, 'Apple MagSafe Battery Pack', 'MagSafe Battery Pack - Pin sạc dự phòng nam châm cho iPhone', 2490000.00, 40, 'https://cdn2.cellphones.com.vn/358x/media/catalog/product/p/i/pin-sac-du-phong-apple-magsafe-mjwy3-3_1.jpg', '{"color": ["Trắng"], "output": "5W wireless", "weight": "115g", "capacity": "1460mAh (11.13Wh)", "features": ["MagSafe Magnetic", "Reverse charging", "Pass-through"], "compatibility": "iPhone 12 trở lên"}', true, 3, 1, NULL, '2025-10-13 02:19:19.908295');
INSERT INTO public.products VALUES (81, 'Apple Watch Series 9 GPS 45mm', 'Apple Watch S9 - Chip S9 mạnh mẽ, Màn hình sáng hơn, Double Tap', 10990000.00, 30, 'https://cdn2.cellphones.com.vn/358x/media/catalog/product/v/n/vn_apple_watch_series_9_gps_41mm_starlight_aluminum_starlight_sport_band_pdp_image_position-1_1.jpg', '{"chip": "Apple S9 SiP", "color": ["Midnight", "Starlight", "Pink", "Red"], "screen": "45mm Retina LTPO OLED", "battery": "Lên đến 18 giờ", "sensors": ["ECG", "Blood Oxygen", "Temperature"], "features": ["Double Tap", "Crash Detection", "Always-On Display", "WR50"], "connectivity": "GPS, Bluetooth 5.3"}', true, 4, 1, NULL, '2025-10-13 02:19:44.318331');
INSERT INTO public.products VALUES (82, 'Samsung Galaxy Watch6 Classic 47mm', 'Galaxy Watch6 Classic - Vòng bezel xoay, Theo dõi sức khỏe toàn diện', 8990000.00, 25, 'https://cdn2.cellphones.com.vn/358x/media/catalog/product/s/m/sm-r960_001_front_silver_3.png', '{"chip": "Exynos W930", "color": ["Black", "Silver"], "screen": "47mm Super AMOLED", "battery": "Lên đến 40 giờ", "sensors": ["BioActive", "ECG", "Body Composition"], "features": ["Rotating Bezel", "Sleep Coaching", "Fall Detection", "5ATM"], "connectivity": "Bluetooth 5.3"}', true, 4, 2, NULL, '2025-10-13 02:20:04.900343');
INSERT INTO public.products VALUES (83, 'Xiaomi Watch 2 Pro', 'Xiaomi Watch 2 Pro - HyperOS, Snapdragon W5+, Màn hình AMOLED', 6990000.00, 35, 'https://cdn2.cellphones.com.vn/358x/media/catalog/product/w/a/watch_2.png', '{"chip": "Snapdragon W5+ Gen 1", "color": ["Black", "Silver"], "screen": "1.43 inch AMOLED", "battery": "Lên đến 65 giờ", "sensors": ["Heart Rate", "SpO2", "GPS"], "features": ["HyperOS", "150+ Sports Modes", "5ATM + IP68"], "connectivity": "Bluetooth 5.2, WiFi"}', true, 4, 3, NULL, '2025-10-13 02:20:31.254298');
INSERT INTO public.products VALUES (84, 'OPPO Watch 3 Pro', 'OPPO Watch 3 Pro - Màn hình cong 3D, UDDE Dual-core', 7990000.00, 20, 'https://cellphones.com.vn/sforum/wp-content/uploads/2022/12/Oppo-Watch-3-pro-glacier-grey.jpeg', '{"chip": "Snapdragon W5 + Apollo 4 Plus", "color": ["Black", "Silver"], "screen": "1.91 inch 3D Curved AMOLED", "battery": "Lên đến 5 ngày", "sensors": ["ECG", "Heart Rate", "SpO2", "Snore Detection"], "features": ["UDDE Dual Engine", "eSIM", "100+ Sports", "5ATM"], "connectivity": "Bluetooth 5.0, WiFi, LTE"}', true, 4, 4, NULL, '2025-10-13 02:21:08.888461');
INSERT INTO public.products VALUES (95, 'iPhone 12 128GB (96%)', 'iPhone 12 - Máy đẹp 96%, Pin 88%, Bảo hành 6 tháng', 11990000.00, 29, 'https://cdn2.cellphones.com.vn/insecure/rs:fill:358:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/i/p/iphone-12.png', '{"os": "iOS 17", "cpu": "Apple A14 Bionic", "ram": "4GB", "color": ["Đen", "Trắng", "Xanh", "Tím"], "camera": "12MP Dual Camera", "screen": "6.1 inch Super Retina XDR OLED", "battery": "2815mAh - 88%", "storage": "128GB", "warranty": "6 tháng", "condition": "96% - Đẹp", "camera_front": "12MP TrueDepth"}', true, 6, 1, NULL, '2025-10-14 13:58:59.105804');
INSERT INTO public.products VALUES (88, 'Xiaomi Pad 6 Pro', 'Xiaomi Pad 6 Pro - Snapdragon 8+ Gen 1, Màn hình 144Hz', 11990000.00, 30, 'https://cdn2.cellphones.com.vn/insecure/rs:fill:358:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/x/i/xiaomi-pad-6-pro.png', '{"ram": "8GB", "chip": "Snapdragon 8+ Gen 1", "color": ["Gold", "Gray", "Blue"], "camera": "50MP + 2MP", "screen": "11 inch LCD 144Hz (2880 x 1800)", "battery": "8600mAh, 67W", "storage": "256GB", "features": ["Dolby Vision", "Quad Speakers", "Keyboard support"], "camera_front": "20MP"}', true, 5, 3, NULL, NULL);
INSERT INTO public.products VALUES (89, 'OPPO Pad Air 2', 'OPPO Pad Air 2 - MediaTek Helio G99, Pin lớn 8000mAh', 6990000.00, 40, 'https://cdn2.cellphones.com.vn/insecure/rs:fill:358:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/o/p/oppo-pad-air-2.png', '{"ram": "8GB", "chip": "MediaTek Helio G99", "color": ["Gray", "Purple"], "camera": "8MP", "screen": "11.4 inch LCD (2408 x 1720)", "battery": "8000mAh, 33W", "storage": "128GB", "features": ["Quad Speakers", "Kids Space", "Reading Mode"], "camera_front": "8MP"}', true, 5, 4, NULL, NULL);
INSERT INTO public.products VALUES (87, 'Samsung Galaxy Tab S9 FE+', 'Galaxy Tab S9 FE+ - Màn hình lớn 12.4 inch, S Pen đi kèm', 13990000.00, 25, 'https://cdn2.cellphones.com.vn/358x/media/catalog/product/t/a/tab-s9-fe-plus-xam_1.png', '{"ram": "8GB", "chip": "Exynos 1380", "color": ["Gray", "Mint", "Silver"], "camera": "8MP + 8MP Ultra Wide", "screen": "12.4 inch TFT (2560 x 1600)", "battery": "10090mAh, 45W", "storage": "128GB", "features": ["S Pen", "DeX Mode", "IP68"], "camera_front": "12MP"}', true, 5, 2, NULL, '2025-10-13 02:22:28.310951');
INSERT INTO public.products VALUES (90, 'Lenovo Tab P11 Pro Gen 2', 'Lenovo Tab P11 Pro Gen 2 - Màn hình OLED 11.2 inch, JBL Speakers', 9990000.00, 18, 'https://cdn2.cellphones.com.vn/358x/media/catalog/product/l/e/lenovo-tab-p11-pro.png', '{"ram": "6GB", "chip": "MediaTek Kompanio 1300T", "color": ["Storm Gray"], "camera": "13MP + 5MP", "screen": "11.2 inch OLED (2560 x 1536)", "battery": "8200mAh, 30W", "storage": "128GB", "features": ["OLED Display", "JBL Quad Speakers", "Precision Pen 3"], "camera_front": "8MP"}', true, 5, 12, NULL, '2025-10-13 02:22:54.856067');
INSERT INTO public.products VALUES (92, 'Samsung Galaxy S22 Ultra 256GB (98%)', 'Galaxy S22 Ultra - Máy đẹp 98%, S Pen, Bảo hành 6 tháng', 15990000.00, 18, 'https://cdn2.cellphones.com.vn/358x/media/catalog/product/s/m/sm-s908_galaxys22ultra_front_green_211119_1_1.jpg', '{"os": "Android 14, One UI 6", "cpu": "Snapdragon 8 Gen 1", "ram": "12GB", "color": ["Phantom Black", "Burgundy"], "camera": "108MP Quad Camera", "screen": "6.8 inch Dynamic AMOLED 2X", "battery": "5000mAh", "storage": "256GB", "warranty": "6 tháng", "condition": "98% - Đẹp", "camera_front": "40MP"}', true, 6, 2, NULL, '2025-10-13 02:24:12.085217');
INSERT INTO public.products VALUES (93, 'MacBook Air M1 2020 8GB 256GB (95%)', 'MacBook Air M1 - Máy đẹp 95%, Pin 85%, Bảo hành 3 tháng', 16990000.00, 12, 'https://cdn2.cellphones.com.vn/358x/media/catalog/product/m/a/macbook-air-silver-select-201810_1.jpg', '{"os": "macOS Sonoma", "cpu": "Apple M1 8-core", "gpu": "7-core GPU", "ram": "8GB", "color": ["Space Gray", "Silver", "Gold"], "screen": "13.3 inch Retina", "battery": "49.9Wh - 85%", "storage": "256GB SSD", "warranty": "3 tháng", "condition": "95% - Đẹp"}', true, 6, 1, NULL, '2025-10-13 02:25:02.081946');
INSERT INTO public.products VALUES (94, 'iPad Gen 9 WiFi 64GB (97%)', 'iPad Gen 9 - Máy đẹp 97%, Pin 90%, Bảo hành 3 tháng', 6990000.00, 25, 'https://cdn2.cellphones.com.vn/358x/media/catalog/product/x/_/x_mmas_1.png', '{"ram": "3GB", "chip": "Apple A13 Bionic", "color": ["Space Gray", "Silver"], "camera": "8MP Wide", "screen": "10.2 inch Retina", "battery": "32.4Wh - 90%", "storage": "64GB", "warranty": "3 tháng", "condition": "97% - Đẹp", "camera_front": "12MP Ultra Wide"}', true, 6, 1, NULL, '2025-10-13 02:25:23.060968');
INSERT INTO public.products VALUES (91, 'iPhone 13 Pro Max 256GB (99%)', 'iPhone 13 Pro Max - Máy đẹp 99%, Pin 100%, Bảo hành 6 tháng', 19990000.00, 14, 'https://cdn2.cellphones.com.vn/insecure/rs:fill:358:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/i/p/iphone-13-pro-max.png', '{"os": "iOS 17", "cpu": "Apple A15 Bionic", "ram": "6GB", "color": ["Xanh Sierra", "Vàng", "Bạc", "Graphite"], "camera": "12MP Triple Camera", "screen": "6.7 inch Super Retina XDR OLED", "battery": "4352mAh", "storage": "256GB", "warranty": "6 tháng", "condition": "99% - Như mới", "camera_front": "12MP TrueDepth"}', true, 6, 1, NULL, '2025-10-13 03:18:12.161575');
INSERT INTO public.products VALUES (62, 'Samsung Galaxy S24 Ultra 12GB 256GB', 'Galaxy S24 Ultra - AI mạnh mẽ, Camera 200MP, Bút S Pen tích hợp', 27990000.00, 24, 'https://cdn2.cellphones.com.vn/358x/media/catalog/product/g/a/galaxy-s24-ultra-vang_1_3.png', '{"os": "Android 14, One UI 6.1", "cpu": "Snapdragon 8 Gen 3 for Galaxy", "ram": "12GB", "color": ["Xám Titan", "Vàng Titan", "Tím", "Đen Titan"], "camera": "Camera chính: 200MP, 50MP, 12MP, 10MP", "screen": "6.8 inch, Dynamic AMOLED 2X", "weight": "232g", "battery": "5000mAh, Sạc nhanh 45W", "storage": "256GB", "camera_front": "12MP"}', true, 1, 2, NULL, '2025-10-14 01:42:32.642069');
INSERT INTO public.products VALUES (99, 'Samsung Odyssey G7 32" Curved 240Hz', 'Samsung 32" Curved Gaming - QLED, 240Hz, 1000R, G-Sync', 17990000.00, 8, 'https://tandoanh.vn/wp-content/uploads/2020/07/M%C3%A0n-h%C3%ACnh-Samsung-3222-Odyssey-G7-Gaming-Monitor-01.jpg', '{"size": "32 inch", "color": ["Black"], "panel": "VA Curved 1000R", "ports": ["DisplayPort 1.4", "2x HDMI 2.0", "USB Hub"], "features": ["QLED Quantum Dot", "G-Sync Compatible", "HDR600"], "resolution": "2560 x 1440 (QHD)", "refresh_rate": "240Hz", "response_time": "1ms (GtG)"}', true, 7, 2, NULL, '2025-10-13 01:59:59.884485');
INSERT INTO public.products VALUES (102, 'Lenovo ThinkBook 16 G8 IAL - 21SK0070VN', 'Laptop doanh nhân 16 inch, hiệu năng ổn định, Intel Core i5-125H, RAM 16GB, SSD 1TB, màn WUXGA 60Hz.', 25390000.00, 15, 'https://lh3.googleusercontent.com/c4wxRwl3vcq5itOWRKJHINaKLCFrm7DfWDY8E1MEBb8sXErCOh-3J4nI9oZ29PKS7_xKAC9viNOHwe8_jDFKTdVcC5orgXU=w1000-rw', '{"os": "Windows 11 Home", "cpu": "Intel Core i5-125H", "gpu": "Intel Iris Xe", "ram": "16GB", "screen": "16 inch WUXGA IPS 60Hz", "weight": "1.8 kg", "storage": "1TB SSD"}', true, 2, 12, NULL, '2025-10-13 04:43:43.040727');
INSERT INTO public.products VALUES (98, 'Dell UltraSharp U2723DE 27" QHD', 'Dell UltraSharp 27" QHD - IPS Black, USB-C Hub, ComfortView Plus', 13990000.00, 12, 'https://cdn2.cellphones.com.vn/358x/media/catalog/product/5/4/54_1_21.jpg?_gl=1*1d6q3ts*_gcl_aw*R0NMLjE3NjAzMjA4ODYuQ2owS0NRandvNjNIQmhDS0FSSXNBSE9IVl9VSXJ6RFFUZ2V6VFlnTVhiZEhkRUdJYzJIanBuZmtmbGp6RFlycXBmVGt2TGZBTU1KYTNYNGFBbXpPRUFMd193Y0I.*_gcl_au*MTk0MTg2MTIwMC4xNzU2NDc3Mjc3*_ga*ODYwNjA3MDA2LjE3NDM0NDQxOTY.*_ga_QLK8WFHNK9*czE3NjAzMjA4ODYkbzI2JGcxJHQxNzYwMzIxMjAwJGo1OCRsMCRoMTUwMTgwMTc0Ng..', '{"size": "27 inch", "color": ["Platinum Silver"], "panel": "IPS Black", "ports": ["DisplayPort", "HDMI", "USB-C", "USB Hub", "RJ45"], "features": ["USB-C 90W", "ComfortView Plus", "99% sRGB", "RJ45 Ethernet"], "resolution": "2560 x 1440 (QHD)", "refresh_rate": "60Hz", "response_time": "5ms"}', true, 7, 13, NULL, '2025-10-13 02:12:56.869653');
INSERT INTO public.products VALUES (68, 'MacBook Air 15 inch M3 2024 8GB 256GB', 'MacBook Air 15" - Chip M3 mạnh mẽ, Màn hình Liquid Retina 15.3 inch', 32990000.00, 20, 'https://cdn2.cellphones.com.vn/358x/media/catalog/product/t/e/text_ng_n_2__5_39.png?_gl=1*1udpn8m*_gcl_aw*R0NMLjE3NjAzMjA4ODYuQ2owS0NRandvNjNIQmhDS0FSSXNBSE9IVl9VSXJ6RFFUZ2V6VFlnTVhiZEhkRUdJYzJIanBuZmtmbGp6RFlycXBmVGt2TGZBTU1KYTNYNGFBbXpPRUFMd193Y0I.*_gcl_au*MTk0MTg2MTIwMC4xNzU2NDc3Mjc3*_ga*ODYwNjA3MDA2LjE3NDM0NDQxOTY.*_ga_QLK8WFHNK9*czE3NjAzMjA4ODYkbzI2JGcxJHQxNzYwMzIxNjQ1JGo1NiRsMCRoMTUwMTgwMTc0Ng..', '{"os": "macOS Sonoma", "cpu": "Apple M3 8-core CPU", "gpu": "10-core GPU", "ram": "8GB unified memory", "color": ["Xám", "Vàng", "Bạc", "Xanh"], "ports": ["2x Thunderbolt/USB 4", "MagSafe 3", "3.5mm audio"], "screen": "15.3 inch, Liquid Retina (2880 x 1864)", "weight": "1.51 kg", "battery": "66.5Wh, Lên đến 18 giờ", "storage": "256GB SSD"}', true, 2, 1, NULL, '2025-10-13 02:14:36.847162');
INSERT INTO public.products VALUES (72, 'Lenovo ThinkPad X1 Carbon Gen 11', 'ThinkPad X1 Carbon - Doanh nhân cao cấp, Bền bỉ, Bảo mật tốt', 52990000.00, 10, 'https://www.laptopvip.vn/images/ab__webp/detailed/31/01-X1-Carbon-G11-Hero-Front-Facing-x1qh-gu-www.laptopvip.vn-1677831914.webp', '{"os": "Windows 11 Pro", "cpu": "Intel Core i7-1365U vPro", "gpu": "Intel Iris Xe Graphics", "ram": "32GB LPDDR5", "color": ["Black"], "ports": ["2x Thunderbolt 4", "2x USB-A", "HDMI"], "screen": "14 inch, WUXGA IPS", "weight": "1.12 kg", "battery": "57Wh", "storage": "1TB SSD"}', true, 2, 12, NULL, '2025-10-13 02:17:16.840764');
INSERT INTO public.products VALUES (86, 'iPad Pro 11 inch M2 WiFi 128GB', 'iPad Pro 11" M2 - Hiệu năng đỉnh cao, Màn hình Liquid Retina', 21990000.00, 20, 'https://cdn2.cellphones.com.vn/358x/media/catalog/product/b/a/bacjccc.jpg', '{"ram": "8GB", "chip": "Apple M2", "color": ["Space Gray", "Silver"], "camera": "12MP Wide + 10MP Ultra Wide", "screen": "11 inch Liquid Retina (2388 x 1668)", "battery": "Lên đến 10 giờ", "storage": "128GB", "features": ["ProMotion 120Hz", "Face ID", "USB-C Thunderbolt"], "camera_front": "12MP TrueDepth"}', true, 5, 1, NULL, '2025-10-13 02:21:39.4806');
INSERT INTO public.products VALUES (103, 'Acer Aspire 16 A16-71M-71U7', 'Laptop văn phòng cao cấp, Intel Core i7-155H, RAM 16GB, SSD 512GB, màn WUXGA IPS 60Hz.', 21590000.00, 18, 'https://lh3.googleusercontent.com/9KrMwDr98CZh8qGPpr5LBNtNO-aD-K6BL17YHfjdBmdKjyIgWTovt6Prki9bQIUO-fqzGqm36VMWQ8H_JVRMsYCFZAUc1W8T=w1000-rw', '{"os": "Windows 11 Home", "cpu": "Intel Core i7-155H", "gpu": "Intel Iris Xe", "ram": "16GB", "screen": "16 inch WUXGA IPS 60Hz", "weight": "1.8 kg", "storage": "512GB SSD"}', true, 2, 15, NULL, '2025-10-13 04:46:42.370077');
INSERT INTO public.products VALUES (104, 'Lenovo Legion 5 15IRX10 - 83LY00HWVN', 'Laptop gaming cao cấp, RTX 4060, Intel Core i7-14700HX, màn 15.6 inch WQXGA OLED 165Hz.', 40490000.00, 10, 'https://lh3.googleusercontent.com/s8Jn6MSs3_koF7_ryfT51QMQa0vGF4pYQ9gU3lHoDBQAA8sal1eBohBVTCA8tmNJcdcn1fFfhk6YLqtsbccScKZh1KK8Zknq=w1000-rw', '{"os": "Windows 11 Home", "cpu": "Intel Core i7-14700HX", "gpu": "RTX 4060 8GB", "ram": "16GB DDR5", "screen": "15.6 inch WQXGA OLED 165Hz", "weight": "2.4 kg", "storage": "512GB SSD"}', true, 2, 12, NULL, '2025-10-13 04:47:11.89749');
INSERT INTO public.products VALUES (105, 'Lenovo LOQ 15IRX10 - 83JE00PEVN', 'Laptop gaming phổ thông, RTX 4050, Intel Core i7-13650HX, màn 15.6 inch FHD 144Hz.', 31090000.00, 12, 'https://lh3.googleusercontent.com/lZoZAHYrNBdQY7Ea0fd7ndaGGIC_9eyStvfadTkdXxlqB6fDqxXRz87JJPYuZkKBASZulprwZIUiRSf2C5NSJjAKA05VHBs=w1000-rw', '{"os": "Windows 11 Home", "cpu": "Intel Core i7-13650HX", "gpu": "RTX 4050 6GB", "ram": "16GB DDR5", "screen": "15.6 inch FHD IPS 144Hz", "weight": "2.3 kg", "storage": "512GB SSD"}', true, 2, 12, NULL, '2025-10-13 04:48:38.284297');
INSERT INTO public.products VALUES (106, 'Acer Aspire Go 14 AI AG14-71M-57WR', 'Laptop học tập văn phòng mỏng nhẹ, Intel Core i5-125H, RAM 16GB, SSD 512GB, màn 14 inch 60Hz.', 16790000.00, 25, 'https://lh3.googleusercontent.com/zGINdaTDd4Jfd_kckUggS59oAQEqw1RmG-WjV4e9YSbQdCMmrUk-lYkR0TRCaZ1eoWyeENdIiUgtMHP95YaDzHv0EKQRgVWefA=w1000-rw', '{"os": "Windows 11 Home", "cpu": "Intel Core i5-125H", "gpu": "Intel Iris Xe", "ram": "16GB", "screen": "14 inch WUXGA IPS 60Hz", "weight": "1.4 kg", "storage": "512GB SSD"}', true, 2, 15, NULL, '2025-10-13 04:49:12.082509');
INSERT INTO public.products VALUES (112, 'iPhone 17 Pro 256GB', 'iPhone 17 Pro - Màn hình ProMotion 120Hz, Chip A19 Pro, thiết kế Titan mới.', 34990000.00, 19, 'https://cdn.tgdd.vn/Products/Images/42/342676/iphone-17-pro-cam-thumb-600x600.jpg', '{"os": "iOS 18", "cpu": "Apple A19 Pro", "ram": "8GB", "color": ["Titan Xanh", "Titan Cát", "Titan Đen"], "camera": "48MP + 12MP + 12MP", "screen": "6.1 inch Super Retina XDR OLED 120Hz", "battery": "4200mAh", "storage": "256GB", "camera_front": "12MP"}', true, 1, 1, NULL, '2025-10-14 14:36:26.688384');
INSERT INTO public.products VALUES (110, 'Lenovo ThinkPad E14 Gen 7 - 21JXS02XVN', 'Laptop doanh nhân Lenovo, Intel Core i5-1335U, RAM 16GB, SSD 512GB, thiết kế bền bỉ chuẩn MIL-STD.', 20990000.00, 15, 'https://lh3.googleusercontent.com/e_s-eDmS11J-tPPGCIk3UC7s0NZZfR5MGOZ1Y-qOPnYZU2hdfg06SAvw_6RZtdmUNcccOImKA5mBtXOOG8W6s_MPGgbJJ750pA=w1000-rw', '{"os": "Windows 11 Home", "cpu": "Intel Core i5-1335U", "gpu": "Intel Iris Xe", "ram": "16GB", "screen": "14 inch FHD IPS 60Hz", "weight": "1.6 kg", "storage": "512GB SSD"}', true, 2, 12, NULL, '2025-10-13 04:44:43.375981');
INSERT INTO public.products VALUES (108, 'Acer Aspire Go 14 AI AG14-71M-71T8', 'Laptop văn phòng, Intel Core i7-155H, RAM 16GB, SSD 512GB, 14 inch WUXGA IPS 60Hz.', 19790000.00, 22, 'https://lh3.googleusercontent.com/iGRESHJWoKJGMFigil8Mp4d2qRFi3mGul3Yq4zaC4SjF2OW35p4OHu8O2R5UOqbzXSrtDnrR_P4n4uRnismUZ8AhjVVxd461=w1000-rw', '{"os": "Windows 11 Home", "cpu": "Intel Core i7-155H", "gpu": "Intel Iris Xe", "ram": "16GB", "screen": "14 inch WUXGA IPS 60Hz", "weight": "1.45 kg", "storage": "512GB SSD"}', true, 2, 15, NULL, '2025-10-13 04:45:36.488783');
INSERT INTO public.products VALUES (109, 'Lenovo ThinkPad E14 Gen 7 - 21JXS02VUN', 'Laptop doanh nhân cao cấp, Intel Core i5-1335U, RAM 16GB, SSD 512GB, màn FHD 60Hz.', 22990000.00, 10, 'https://lh3.googleusercontent.com/ogjIxAqbwwnqsQgWzjEg-2qMFxFNFJ2T3st6K6WOowAeKlSb46rUN5hNHVp8VL9C7Z-2I4aC03XFCxZc4WVc_iZdHVlu8C9t=w1000-rw', '{"os": "Windows 11 Pro", "cpu": "Intel Core i5-1335U", "gpu": "Intel Iris Xe", "ram": "16GB", "screen": "14 inch FHD IPS 60Hz", "weight": "1.6 kg", "storage": "512GB SSD"}', true, 2, 12, NULL, '2025-10-13 04:46:03.575818');
INSERT INTO public.products VALUES (107, 'MSI Modern 15 F13MG-676VN', 'Laptop văn phòng hiệu năng tốt, Intel Core i5-1334U, RAM 16GB, SSD 512GB, màn 15.6 inch FHD.', 16190000.00, 20, 'https://lh3.googleusercontent.com/4omLcE0NC6pRtrkROefiqFbl92Rs7lbioRrZacUgN8JnRt0qbJ3u2M71Ogg8I21WS-RZbYpw4sIkv-veCP30tu16Hb1kh7twBg=w1000-rw', '{"os": "Windows 11 Home", "cpu": "Intel Core i5-1334U", "gpu": "Intel Iris Xe", "ram": "16GB", "screen": "15.6 inch FHD IPS", "weight": "1.7 kg", "storage": "512GB SSD"}', true, 2, 14, NULL, '2025-10-13 04:48:05.19018');
INSERT INTO public.products VALUES (111, 'iPhone 16 Pro Max 256GB', 'iPhone 16 Pro Max - Super Retina XDR, Chip A18 Pro, khung Titan, Camera 48MP.', 30590000.00, 25, 'https://cdn2.cellphones.com.vn/x/media/catalog/product/i/p/iphone-16-pro-max.png', '{"os": "iOS 18", "cpu": "Apple A18 Pro", "ram": "8GB", "color": ["Titan Tự nhiên", "Titan Đen", "Titan Trắng", "Titan Cát"], "camera": "48MP + 12MP + 12MP", "screen": "6.7 inch Super Retina XDR OLED", "battery": "4422mAh", "storage": "256GB", "camera_front": "12MP"}', true, 1, 1, NULL, NULL);
INSERT INTO public.products VALUES (113, 'iPhone Air 256GB', 'iPhone Air - Siêu mỏng nhẹ, chip A18, camera 48MP, pin tối ưu hóa.', 31990000.00, 18, 'https://cdn2.cellphones.com.vn/x/media/catalog/product/i/p/iphone-air.png', '{"os": "iOS 18", "cpu": "Apple A18", "ram": "8GB", "color": ["Trắng", "Xanh", "Hồng"], "camera": "48MP + 12MP", "screen": "6.1 inch Super Retina XDR OLED", "battery": "4000mAh", "storage": "256GB", "camera_front": "12MP"}', true, 1, 1, NULL, NULL);
INSERT INTO public.products VALUES (114, 'iPhone 17 256GB', 'iPhone 17 - Màn hình 6.1 inch, chip A18, camera 48MP, thiết kế mỏng nhẹ.', 24990000.00, 30, 'https://cdn.tgdd.vn/Products/Images/42/342667/iphone-17-xanh-duong-thumb-2-600x600.jpg', '{"os": "iOS 18", "cpu": "Apple A18", "ram": "6GB", "color": ["Xanh", "Hồng", "Trắng", "Đen"], "camera": "48MP + 12MP", "screen": "6.1 inch Super Retina XDR OLED", "battery": "3900mAh", "storage": "256GB", "camera_front": "12MP"}', true, 1, 1, NULL, '2025-10-13 05:02:16.373537');
INSERT INTO public.products VALUES (115, 'Samsung Galaxy S25 FE 5G 8GB/128GB', 'Galaxy S25 FE - AI hỗ trợ chụp ảnh, chip Exynos 2400, camera 50MP.', 14390000.00, 35, 'https://cdn.tgdd.vn/Products/Images/42/342560/samsung-galaxy-s25-fe-blue-thumbai-600x600.jpg', '{"os": "Android 15, One UI 7", "cpu": "Exynos 2400", "ram": "8GB", "color": ["Trắng", "Xanh", "Tím"], "camera": "50MP + 12MP + 8MP", "screen": "6.7 inch FHD+ AMOLED 120Hz", "battery": "4500mAh 25W", "storage": "128GB", "camera_front": "32MP"}', true, 1, 2, NULL, '2025-10-13 05:02:34.145753');
INSERT INTO public.products VALUES (116, 'Samsung Galaxy A17 5G 8GB/128GB', 'Galaxy A17 5G - Màn 6.67 inch, chip Dimensity 6100+, pin 5000mAh, camera kép.', 5990000.00, 50, 'https://cdn.tgdd.vn/Products/Images/42/341688/galaxy-a17-5g-gray-thumbai-600x600.jpg', '{"os": "Android 14", "cpu": "Dimensity 6100+", "ram": "8GB", "color": ["Xanh", "Đen", "Bạc"], "camera": "50MP + 2MP", "screen": "6.67 inch FHD+ AMOLED 120Hz", "battery": "5000mAh", "storage": "128GB", "camera_front": "8MP"}', true, 1, 2, NULL, '2025-10-13 05:02:55.069389');
INSERT INTO public.products VALUES (117, 'OPPO A6 Pro 8GB/128GB', 'OPPO A6 Pro - Thiết kế trẻ trung, camera AI 64MP, pin 5000mAh, sạc nhanh 67W.', 7490000.00, 45, 'https://cdn.tgdd.vn/Products/Images/42/344651/oppo-a6-pro-4g-pink-thumbai-600x600.jpg', '{"os": "Android 14, ColorOS 14", "cpu": "Snapdragon 6 Gen 1", "ram": "8GB", "color": ["Hồng", "Xanh"], "camera": "64MP + 2MP", "screen": "6.7 inch FHD+ AMOLED 120Hz", "battery": "5000mAh 67W", "storage": "128GB", "camera_front": "16MP"}', true, 1, 4, NULL, '2025-10-13 05:03:11.410173');
INSERT INTO public.products VALUES (118, 'OPPO A6 Pro 5G 12GB/256GB', 'OPPO A6 Pro bản cao cấp, chip Snapdragon 6 Gen 1, RAM 12GB, màn 120Hz.', 8990000.00, 40, 'https://cdn.tgdd.vn/Products/Images/42/344649/oppo-a6-pro-5g-pink-thumbai-600x600.jpg', '{"os": "Android 14, ColorOS 14", "cpu": "Snapdragon 6 Gen 1", "ram": "12GB", "color": ["Xanh", "Hồng"], "camera": "64MP + 2MP", "screen": "6.7 inch FHD+ AMOLED 120Hz", "battery": "5000mAh 67W", "storage": "256GB", "camera_front": "16MP"}', true, 1, 4, NULL, '2025-10-13 05:03:30.066336');
INSERT INTO public.products VALUES (126, 'iPhone 17 Pro Max 256GB', 'iPhone 17 Pro Max - Super Retina XDR, chip A19 Pro, khung Titan, camera 48MP.', 37990000.00, 20, 'https://cdn2.cellphones.com.vn/x/media/catalog/product/i/p/iphone-17-pro-max.png', '{"os": "iOS 18", "cpu": "Apple A19 Pro", "ram": "8GB", "color": ["Titan Cát", "Titan Đen", "Titan Trắng"], "camera": "48MP + 12MP + 12MP", "screen": "6.7 inch Super Retina XDR OLED", "battery": "4422mAh", "storage": "256GB", "camera_front": "12MP"}', true, 1, 1, NULL, NULL);
INSERT INTO public.products VALUES (127, 'iPhone 16 128GB', 'iPhone 16 - Màn 6.1 inch Super Retina XDR, chip A18, camera 48MP, pin tối ưu.', 20990000.00, 30, 'https://cdn2.cellphones.com.vn/x/media/catalog/product/i/p/iphone-16.png', '{"os": "iOS 18", "cpu": "Apple A18", "ram": "6GB", "color": ["Xanh", "Tím", "Đen"], "camera": "48MP + 12MP", "screen": "6.1 inch Super Retina XDR OLED", "battery": "3800mAh", "storage": "128GB", "camera_front": "12MP"}', true, 1, 1, NULL, NULL);
INSERT INTO public.products VALUES (120, 'Vivo V60 5G 12GB/256GB', 'Vivo V60 5G - Camera chân dung Aura Light, chip Dimensity 8300, màn AMOLED 120Hz.', 15900000.00, 30, 'https://cdn.tgdd.vn/Products/Images/42/341625/vivo-v60-5g-xam-thumb-600x600.jpg', '{"os": "Android 14, Funtouch OS 14", "cpu": "Dimensity 8300", "ram": "12GB", "color": ["Đen", "Vàng Đồng"], "camera": "50MP + 8MP", "screen": "6.78 inch AMOLED 120Hz", "battery": "5000mAh 80W", "storage": "256GB", "camera_front": "32MP"}', true, 1, 5, NULL, '2025-10-13 05:04:04.860131');
INSERT INTO public.products VALUES (121, 'Xiaomi 15T 5G 12GB/256GB', 'Xiaomi 15T 5G - Màn AMOLED 6.83 inch, Snapdragon 8 Gen 3, camera Leica 50MP, sạc 120W.', 13990000.00, 30, 'https://cdn.tgdd.vn/Products/Images/42/344644/xiaomi-15t-rose-gold-thumb-600x600.jpg', '{"os": "Android 14, HyperOS", "cpu": "Snapdragon 8 Gen 3", "ram": "12GB", "color": ["Đen", "Vàng Đồng"], "camera": "50MP + 50MP + 50MP", "screen": "6.83 inch AMOLED 144Hz", "battery": "5000mAh 120W", "storage": "256GB", "camera_front": "32MP"}', true, 1, 3, NULL, '2025-10-13 05:04:27.889549');
INSERT INTO public.products VALUES (128, 'Samsung Galaxy A07 4GB/64GB', 'Galaxy A07 - Màn HD+ 6.67 inch, pin 5000mAh, camera kép, giá rẻ cho học sinh.', 2590000.00, 80, 'https://cdn.tgdd.vn/Products/Images/42/341802/samsung-galaxy-a07-violet-thumb-600x600.jpg', '{"os": "Android 14", "cpu": "Helio G85", "ram": "4GB", "color": ["Xanh", "Đen"], "camera": "50MP + 2MP", "screen": "6.67 inch HD+ PLS", "battery": "5000mAh", "storage": "64GB", "camera_front": "8MP"}', true, 1, 2, NULL, '2025-10-13 05:04:52.297871');
INSERT INTO public.products VALUES (129, 'Samsung Galaxy S25 Ultra 5G 12GB/256GB', 'Galaxy S25 Ultra - Màn Dynamic AMOLED 2X QHD+ 120Hz, Snapdragon 8 Gen 3, bút S-Pen tích hợp.', 27480000.00, 18, 'https://cdn.tgdd.vn/Products/Images/42/333347/samsung-galaxy-s25-ultra-blue-thumbai-600x600.jpg', '{"os": "Android 15, One UI 7", "cpu": "Snapdragon 8 Gen 3", "ram": "12GB", "color": ["Đen", "Xám", "Tím"], "camera": "200MP + 50MP + 12MP + 10MP", "screen": "6.8 inch QHD+ Dynamic AMOLED 120Hz", "battery": "5000mAh 45W", "storage": "256GB", "camera_front": "12MP"}', true, 1, 2, NULL, '2025-10-13 05:05:19.283133');
INSERT INTO public.products VALUES (130, 'OPPO Reno14 F 5G 8GB/256GB', 'OPPO Reno14 F - Màn 6.67 inch AMOLED, chip Dimensity 6300, camera 64MP AI, sạc nhanh 67W.', 10300000.00, 25, 'https://cdn.tgdd.vn/Products/Images/42/339624/oppo-reno14-f-5g-pink-thumb-600x600.jpg', '{"os": "Android 14, ColorOS 14", "cpu": "Dimensity 6300", "ram": "8GB", "color": ["Hồng", "Xanh"], "camera": "64MP + 2MP", "screen": "6.67 inch FHD+ AMOLED 120Hz", "battery": "5000mAh 67W", "storage": "256GB", "camera_front": "32MP"}', true, 1, 4, NULL, '2025-10-13 05:05:37.131962');
INSERT INTO public.products VALUES (131, 'OPPO Reno14 F 5G 12GB/256GB', 'OPPO Reno14 F - Màn AMOLED 120Hz, chip Dimensity 6300, pin 5000mAh, sạc nhanh 67W.', 11290000.00, 20, 'https://cdn.tgdd.vn/Products/Images/42/339177/oppo-reno14-f-5g-blue-thumb-600x600.jpg', '{"os": "Android 14, ColorOS 14", "cpu": "Dimensity 6300", "ram": "12GB", "color": ["Xanh", "Trắng"], "camera": "64MP + 2MP", "screen": "6.67 inch FHD+ AMOLED 120Hz", "battery": "5000mAh 67W", "storage": "256GB", "camera_front": "32MP"}', true, 1, 4, NULL, '2025-10-13 05:05:55.362869');
INSERT INTO public.products VALUES (132, 'Xiaomi 15T 5G 12GB/512GB', 'Xiaomi 15T 5G - Snapdragon 8 Gen 3, màn 6.83 inch AMOLED, camera Leica 50MP.', 14990000.00, 25, 'https://cdn.tgdd.vn/Products/Images/42/344645/xiaomi-15t-gray-thumb-600x600.jpg', '{"os": "Android 14, HyperOS", "cpu": "Snapdragon 8 Gen 3", "ram": "12GB", "color": ["Xám", "Vàng"], "camera": "50MP + 50MP + 50MP", "screen": "6.83 inch AMOLED 144Hz", "battery": "5000mAh 120W", "storage": "512GB", "camera_front": "32MP"}', true, 1, 3, NULL, '2025-10-13 05:06:13.951386');
INSERT INTO public.products VALUES (133, 'Xiaomi 15T Pro 5G 12GB/512GB', 'Xiaomi 15T Pro - Bản nâng cấp với camera Leica, chip Snapdragon 8 Gen 3, màn AMOLED 144Hz.', 19490000.00, 20, 'https://cdn.tgdd.vn/Products/Images/42/344647/xiaomi-15t-pro-black-thumb-600x600.jpg', '{"os": "Android 14, HyperOS", "cpu": "Snapdragon 8 Gen 3", "ram": "12GB", "color": ["Đen", "Vàng"], "camera": "50MP + 50MP + 50MP", "screen": "6.83 inch AMOLED 144Hz", "battery": "5000mAh 120W", "storage": "512GB", "camera_front": "32MP"}', true, 1, 3, NULL, '2025-10-13 05:06:32.598571');
INSERT INTO public.products VALUES (141, 'iPhone 15 128GB', 'iPhone 15 - Super Retina XDR OLED 6.1 inch, chip A16 Bionic, camera 48MP.', 16990000.00, 35, 'https://cdn2.cellphones.com.vn/x/media/catalog/product/i/p/iphone-15.png', '{"os": "iOS 17", "cpu": "Apple A16 Bionic", "ram": "6GB", "color": ["Đen", "Hồng", "Xanh"], "camera": "48MP + 12MP", "screen": "6.1 inch Super Retina XDR OLED", "battery": "3349mAh", "storage": "128GB", "camera_front": "12MP"}', true, 1, 1, NULL, NULL);
INSERT INTO public.products VALUES (135, 'Vivo Y19s Pro 8GB/128GB', 'Vivo Y19s Pro - Màn 6.68 inch, pin lớn 5000mAh, chip Helio G85, camera 64MP.', 5400000.00, 35, 'https://cdn.tgdd.vn/Products/Images/42/338918/vivo-y19s-pro-bac-thumb-1-600x600.jpg', '{"os": "Android 14, Funtouch OS 14", "cpu": "Helio G85", "ram": "8GB", "color": ["Đen", "Vàng"], "camera": "64MP + 2MP", "screen": "6.68 inch HD+", "battery": "5000mAh 18W", "storage": "128GB", "camera_front": "16MP"}', true, 1, 5, NULL, '2025-10-13 05:07:06.572253');
INSERT INTO public.products VALUES (139, 'iPhone 16e 128GB', 'iPhone 16e - Màn 6.1 inch OLED, chip A17, camera kép 48MP, Face ID.', 15590000.00, 30, 'https://cdn.tgdd.vn/Products/Images/42/334864/iphone-16e-white-thumb-600x600.jpg', '{"os": "iOS 18", "cpu": "Apple A17", "ram": "6GB", "color": ["Trắng", "Xanh", "Đen"], "camera": "48MP + 12MP", "screen": "6.1 inch Super Retina XDR", "battery": "3900mAh", "storage": "128GB", "camera_front": "12MP"}', true, 1, 1, NULL, '2025-10-13 05:07:26.722726');
INSERT INTO public.products VALUES (140, 'Samsung Galaxy Z Fold7 5G 12GB/256GB', 'Galaxy Z Fold7 - Thiết kế gập linh hoạt, Snapdragon 8 Gen 3, màn Dynamic AMOLED kép.', 42900000.00, 12, 'https://cdn.tgdd.vn/Products/Images/42/338738/samsung-galaxy-z-fold7-black-thumb-1-600x600.jpg', '{"os": "Android 15, One UI 7", "cpu": "Snapdragon 8 Gen 3", "ram": "12GB", "color": ["Đen", "Xám"], "camera": "50MP + 12MP + 10MP", "screen": "Màn chính 7.6 inch QXGA+ AMOLED 120Hz + Màn phụ 6.3 inch", "battery": "4400mAh 45W", "storage": "256GB", "camera_front": "10MP + 4MP"}', true, 1, 2, NULL, '2025-10-13 05:07:44.088015');
INSERT INTO public.products VALUES (142, 'Samsung Galaxy A56 5G 12GB/256GB', 'Galaxy A56 5G - Thiết kế mới, chip Snapdragon 7 Gen 1, camera 50MP OIS, pin 5000mAh.', 11080000.00, 28, 'https://cdn.tgdd.vn/Products/Images/42/334932/samsung-galaxy-a56-5g-gray-thumb-1-600x600.jpg', '{"os": "Android 14, One UI 6.1", "cpu": "Snapdragon 7 Gen 1", "ram": "12GB", "color": ["Xanh", "Đen"], "camera": "50MP + 12MP + 5MP", "screen": "6.7 inch FHD+ Super AMOLED 120Hz", "battery": "5000mAh 25W", "storage": "256GB", "camera_front": "32MP"}', true, 1, 2, NULL, '2025-10-13 05:08:02.256144');
INSERT INTO public.products VALUES (143, 'Samsung Galaxy A26 5G 6GB/128GB', 'Galaxy A26 5G - Giá rẻ, chip Dimensity 6100+, pin 5000mAh, màn AMOLED 90Hz.', 5720000.00, 60, 'https://cdn.tgdd.vn/Products/Images/42/335915/samsung-galaxy-a26-5g-black-thumbn-600x600.jpg', '{"os": "Android 14", "cpu": "Dimensity 6100+", "ram": "6GB", "color": ["Xanh", "Đen"], "camera": "50MP + 2MP", "screen": "6.6 inch FHD+ AMOLED 90Hz", "battery": "5000mAh 25W", "storage": "128GB", "camera_front": "8MP"}', true, 1, 2, NULL, '2025-10-13 05:08:17.478341');
INSERT INTO public.products VALUES (144, 'Samsung Galaxy A06 5G 6GB/128GB', 'Galaxy A06 5G - Chip Helio G85, pin 5000mAh, màn 6.6 inch, camera kép.', 3610000.00, 70, 'https://cdn.tgdd.vn/Products/Images/42/335234/samsung-galaxy-a06-5g-black-thumbn-600x600.jpg', '{"os": "Android 14", "cpu": "Helio G85", "ram": "6GB", "color": ["Xanh", "Đen"], "camera": "50MP + 2MP", "screen": "6.6 inch HD+ PLS", "battery": "5000mAh", "storage": "128GB", "camera_front": "8MP"}', true, 1, 2, NULL, '2025-10-13 05:08:32.891235');
INSERT INTO public.products VALUES (146, 'Xiaomi Redmi Note 14 Pro+ 5G 12GB/512GB', 'Redmi Note 14 Pro+ 5G - Màn AMOLED 120Hz, chip Dimensity 7200 Ultra, camera 200MP.', 11860000.00, 20, 'https://cdn.tgdd.vn/Products/Images/42/337713/xiaomi-redmi-note-14-pro-plus-thumbnew-600x600.jpg', '{"os": "Android 14, HyperOS", "cpu": "Dimensity 7200 Ultra", "ram": "12GB", "color": ["Vàng", "Đen"], "camera": "200MP + 8MP + 2MP", "screen": "6.7 inch AMOLED 120Hz", "battery": "5000mAh 120W", "storage": "512GB", "camera_front": "16MP"}', true, 1, 3, NULL, '2025-10-13 05:08:48.233639');
INSERT INTO public.products VALUES (147, 'OPPO A5 8GB/128GB', 'OPPO A5 - Màn 6.67 inch, pin 5000mAh, camera 50MP, chip Snapdragon 680.', 6290000.00, 35, 'https://cdn.tgdd.vn/Products/Images/42/341378/oppo-a5-green-thumbn-600x600.jpg', '{"os": "Android 14, ColorOS 14", "cpu": "Snapdragon 680", "ram": "8GB", "color": ["Xanh", "Đen"], "camera": "50MP + 2MP", "screen": "6.67 inch FHD+", "battery": "5000mAh 33W", "storage": "128GB", "camera_front": "8MP"}', true, 1, 4, NULL, '2025-10-13 05:09:05.581128');
INSERT INTO public.products VALUES (148, 'OPPO Reno14 F 5G 12GB/256GB', 'OPPO Reno14 F - Màn AMOLED 120Hz, chip Dimensity 6300, pin 5000mAh, sạc nhanh 67W.', 11290000.00, 20, 'https://cdn.tgdd.vn/Products/Images/42/339177/oppo-reno14-f-5g-blue-thumb-600x600.jpg', '{"os": "Android 14, ColorOS 14", "cpu": "Dimensity 6300", "ram": "12GB", "color": ["Xanh", "Trắng"], "camera": "64MP + 2MP", "screen": "6.67 inch FHD+ AMOLED 120Hz", "battery": "5000mAh 67W", "storage": "256GB", "camera_front": "32MP"}', true, 1, 4, NULL, '2025-10-13 05:09:45.299412');
INSERT INTO public.products VALUES (149, 'Xiaomi 15T 5G 12GB/512GB', 'Xiaomi 15T 5G - Snapdragon 8 Gen 3, màn 6.83 inch AMOLED, camera Leica 50MP.', 14990000.00, 25, 'https://cdn.tgdd.vn/Products/Images/42/344645/xiaomi-15t-gray-thumb-600x600.jpg', '{"os": "Android 14, HyperOS", "cpu": "Snapdragon 8 Gen 3", "ram": "12GB", "color": ["Xám", "Vàng"], "camera": "50MP + 50MP + 50MP", "screen": "6.83 inch AMOLED 144Hz", "battery": "5000mAh 120W", "storage": "512GB", "camera_front": "32MP"}', true, 1, 3, NULL, '2025-10-13 05:10:04.114939');
INSERT INTO public.products VALUES (158, 'iPhone 15 128GB', 'iPhone 15 - Super Retina XDR OLED 6.1 inch, chip A16 Bionic, camera 48MP.', 16990000.00, 35, 'https://cdn2.cellphones.com.vn/x/media/catalog/product/i/p/iphone-15.png', '{"os": "iOS 17", "cpu": "Apple A16 Bionic", "ram": "6GB", "color": ["Đen", "Hồng", "Xanh"], "camera": "48MP + 12MP", "screen": "6.1 inch Super Retina XDR OLED", "battery": "3349mAh", "storage": "128GB", "camera_front": "12MP"}', true, 1, 1, NULL, NULL);
INSERT INTO public.products VALUES (164, 'OPPO A5 8GB/128GB', 'OPPO A5 - Màn 6.67 inch, pin 5000mAh, camera 50MP, chip Snapdragon 680.', 6290000.00, 35, 'https://cdn.tgdd.vn/Products/Images/42/341378/oppo-a5-green-thumbn-600x600.jpg', '{"os": "Android 14, ColorOS 14", "cpu": "Snapdragon 680", "ram": "8GB", "color": ["Xanh", "Đen"], "camera": "50MP + 2MP", "screen": "6.67 inch FHD+", "battery": "5000mAh 33W", "storage": "128GB", "camera_front": "8MP"}', true, 1, 4, NULL, '2025-10-13 05:00:18.404128');
INSERT INTO public.products VALUES (161, 'Samsung Galaxy A06 5G 6GB/128GB', 'Galaxy A06 5G - Chip Helio G85, pin 5000mAh, màn 6.6 inch, camera kép.', 3610000.00, 70, 'https://cdn.tgdd.vn/Products/Images/42/335234/samsung-galaxy-a06-5g-black-thumbn-600x600.jpg', '{"os": "Android 14", "cpu": "Helio G85", "ram": "6GB", "color": ["Xanh", "Đen"], "camera": "50MP + 2MP", "screen": "6.6 inch HD+ PLS", "battery": "5000mAh", "storage": "128GB", "camera_front": "8MP"}', true, 1, 2, NULL, '2025-10-13 05:00:48.576797');
INSERT INTO public.products VALUES (163, 'Xiaomi Redmi Note 14 Pro+ 5G 12GB/512GB', 'Redmi Note 14 Pro+ 5G - Màn AMOLED 120Hz, chip Dimensity 7200 Ultra, camera 200MP.', 11860000.00, 20, 'https://cdn.tgdd.vn/Products/Images/42/337713/xiaomi-redmi-note-14-pro-plus-thumbnew-600x600.jpg', '{"os": "Android 14, HyperOS", "cpu": "Dimensity 7200 Ultra", "ram": "12GB", "color": ["Vàng", "Đen"], "camera": "200MP + 8MP + 2MP", "screen": "6.7 inch AMOLED 120Hz", "battery": "5000mAh 120W", "storage": "512GB", "camera_front": "16MP"}', true, 1, 3, NULL, '2025-10-13 05:01:26.942417');
INSERT INTO public.products VALUES (119, 'Xiaomi 15T Pro 5G 12GB/256GB', 'Xiaomi 15T Pro - Snapdragon 8 Gen 3, màn 6.83 inch AMOLED, camera Leica 50MP.', 18490000.00, 25, 'https://cdn.tgdd.vn/Products/Images/42/344646/xiaomi-15t-pro-rose-gold-thumb-600x600.jpg', '{"os": "Android 14, HyperOS", "cpu": "Snapdragon 8 Gen 3", "ram": "12GB", "color": ["Đen", "Vàng Đồng"], "camera": "50MP + 50MP + 50MP", "screen": "6.83 inch AMOLED 144Hz", "battery": "5000mAh 120W", "storage": "256GB", "camera_front": "32MP"}', true, 1, 3, NULL, '2025-10-13 05:03:49.061804');
INSERT INTO public.products VALUES (134, 'Xiaomi 15T Pro 5G 12GB/1TB', 'Xiaomi 15T Pro bản cao nhất, bộ nhớ 1TB, hiệu năng hàng đầu Snapdragon 8 Gen 3.', 20490000.00, 15, 'https://cdn.tgdd.vn/Products/Images/42/344648/xiaomi-15t-pro-gray-thumb-600x600.jpg', '{"os": "Android 14, HyperOS", "cpu": "Snapdragon 8 Gen 3", "ram": "12GB", "color": ["Đen", "Vàng"], "camera": "50MP + 50MP + 50MP", "screen": "6.83 inch AMOLED 144Hz", "battery": "5000mAh 120W", "storage": "1TB", "camera_front": "32MP"}', true, 1, 3, NULL, '2025-10-13 05:06:52.123525');
INSERT INTO public.products VALUES (159, 'Samsung Galaxy A56 5G 12GB/256GB', 'Galaxy A56 5G - Thiết kế mới, chip Snapdragon 7 Gen 1, camera 50MP OIS, pin 5000mAh.', 11080000.00, 28, 'https://cdn.tgdd.vn/Products/Images/42/334932/samsung-galaxy-a56-5g-gray-thumb-1-600x600.jpg', '{"os": "Android 14, One UI 6.1", "cpu": "Snapdragon 7 Gen 1", "ram": "12GB", "color": ["Xanh", "Đen"], "camera": "50MP + 12MP + 5MP", "screen": "6.7 inch FHD+ Super AMOLED 120Hz", "battery": "5000mAh 25W", "storage": "256GB", "camera_front": "32MP"}', true, 1, 2, NULL, '2025-10-13 05:09:21.128685');
INSERT INTO public.products VALUES (160, 'Samsung Galaxy A26 5G 6GB/128GB', 'Galaxy A26 5G - Giá rẻ, chip Dimensity 6100+, pin 5000mAh, màn AMOLED 90Hz.', 5720000.00, 60, 'https://cdn.tgdd.vn/Products/Images/42/335915/samsung-galaxy-a26-5g-black-thumbn-600x600.jpg', '{"os": "Android 14", "cpu": "Dimensity 6100+", "ram": "6GB", "color": ["Xanh", "Đen"], "camera": "50MP + 2MP", "screen": "6.6 inch FHD+ AMOLED 90Hz", "battery": "5000mAh 25W", "storage": "128GB", "camera_front": "8MP"}', true, 1, 2, NULL, '2025-10-13 05:10:19.893311');
INSERT INTO public.products VALUES (150, 'Xiaomi 15T Pro 5G 12GB/512GB', 'Xiaomi 15T Pro - Bản nâng cấp với camera Leica, chip Snapdragon 8 Gen 3, màn AMOLED 144Hz.', 19490000.00, 20, 'https://cdn.tgdd.vn/Products/Images/42/344647/xiaomi-15t-pro-black-thumb-600x600.jpg', '{"os": "Android 14, HyperOS", "cpu": "Snapdragon 8 Gen 3", "ram": "12GB", "color": ["Đen", "Vàng"], "camera": "50MP + 50MP + 50MP", "screen": "6.83 inch AMOLED 144Hz", "battery": "5000mAh 120W", "storage": "512GB", "camera_front": "32MP"}', true, 1, 3, NULL, '2025-10-13 05:10:37.179359');
INSERT INTO public.products VALUES (151, 'Xiaomi 15T Pro 5G 12GB/1TB', 'Xiaomi 15T Pro bản cao nhất, bộ nhớ 1TB, hiệu năng hàng đầu Snapdragon 8 Gen 3.', 20490000.00, 15, 'https://cdn.tgdd.vn/Products/Images/42/344648/xiaomi-15t-pro-gray-thumb-600x600.jpg', '{"os": "Android 14, HyperOS", "cpu": "Snapdragon 8 Gen 3", "ram": "12GB", "color": ["Đen", "Vàng"], "camera": "50MP + 50MP + 50MP", "screen": "6.83 inch AMOLED 144Hz", "battery": "5000mAh 120W", "storage": "1TB", "camera_front": "32MP"}', true, 1, 3, NULL, '2025-10-13 05:10:52.361508');
INSERT INTO public.products VALUES (152, 'Vivo Y19s Pro 8GB/128GB', 'Vivo Y19s Pro - Màn 6.68 inch, pin lớn 5000mAh, chip Helio G85, camera 64MP.', 5400000.00, 35, 'https://cdn.tgdd.vn/Products/Images/42/338918/vivo-y19s-pro-bac-thumb-1-600x600.jpg', '{"os": "Android 14, Funtouch OS 14", "cpu": "Helio G85", "ram": "8GB", "color": ["Đen", "Vàng"], "camera": "64MP + 2MP", "screen": "6.68 inch HD+", "battery": "5000mAh 18W", "storage": "128GB", "camera_front": "16MP"}', true, 1, 5, NULL, '2025-10-13 05:11:09.644112');
INSERT INTO public.products VALUES (156, 'iPhone 16e 128GB', 'iPhone 16e - Màn 6.1 inch OLED, chip A17, camera kép 48MP, Face ID.', 15590000.00, 30, 'https://cdn.tgdd.vn/Products/Images/42/334864/iphone-16e-white-thumb-600x600.jpg', '{"os": "iOS 18", "cpu": "Apple A17", "ram": "6GB", "color": ["Trắng", "Xanh", "Đen"], "camera": "48MP + 12MP", "screen": "6.1 inch Super Retina XDR", "battery": "3900mAh", "storage": "128GB", "camera_front": "12MP"}', true, 1, 1, NULL, '2025-10-13 05:11:24.685905');
INSERT INTO public.products VALUES (157, 'Samsung Galaxy Z Fold7 5G 12GB/256GB', 'Galaxy Z Fold7 - Thiết kế gập linh hoạt, Snapdragon 8 Gen 3, màn Dynamic AMOLED kép.', 42900000.00, 12, 'https://cdn.tgdd.vn/Products/Images/42/338738/samsung-galaxy-z-fold7-black-thumb-1-600x600.jpg', '{"os": "Android 15, One UI 7", "cpu": "Snapdragon 8 Gen 3", "ram": "12GB", "color": ["Đen", "Xám"], "camera": "50MP + 12MP + 10MP", "screen": "Màn chính 7.6 inch QXGA+ AMOLED 120Hz + Màn phụ 6.3 inch", "battery": "4400mAh 45W", "storage": "256GB", "camera_front": "10MP + 4MP"}', true, 1, 2, NULL, '2025-10-13 05:11:51.190835');
INSERT INTO public.products VALUES (166, 'iPad Pro M4 13 inch WiFi 256GB', 'iPad Pro M4 13 inch WiFi - Chip M4, màn Ultra Retina XDR, hỗ trợ Apple Pencil Pro.', 36290000.00, 25, 'https://cdn.tgdd.vn/Products/Images/522/325517/ipad-pro-13-inch-m4-wifi-black-thumb-600x600.jpg', '{"os": "iPadOS 18", "cpu": "Apple M4", "ram": "8GB", "color": ["Bạc", "Xám"], "screen": "13 inch Ultra Retina XDR", "battery": "10 giờ", "storage": "256GB", "connectivity": "WiFi 6E"}', true, 5, 1, NULL, '2025-10-13 05:16:14.959487');
INSERT INTO public.products VALUES (165, 'iPad Pro M4 13 inch 5G 256GB', 'iPad Pro M4 13 inch - Chip Apple M4, màn Ultra Retina XDR, hỗ trợ 5G, hiệu năng vượt trội.', 40690000.00, 20, 'https://cdn.tgdd.vn/Products/Images/522/325534/ipad-pro-13-inch-m4-lte-sliver-thumb-600x600.jpg', '{"os": "iPadOS 18", "cpu": "Apple M4", "ram": "8GB", "color": ["Bạc", "Đen"], "screen": "13 inch Ultra Retina XDR", "battery": "Lên đến 10 giờ", "storage": "256GB", "connectivity": "5G, WiFi 6E"}', true, 5, 1, NULL, '2025-10-13 05:15:57.566369');
INSERT INTO public.products VALUES (167, 'Samsung Galaxy Tab S11 Ultra 5G 12GB/256GB', 'Galaxy Tab S11 Ultra - Màn Dynamic AMOLED 14.6 inch, chip Snapdragon 8 Gen 3, hỗ trợ bút S-Pen.', 34990000.00, 18, 'https://cdn.tgdd.vn/Products/Images/522/344725/samsung-galaxy-tab-s11-ultra-gray-thumb-600x600.jpg', '{"os": "Android 15, One UI 7", "cpu": "Snapdragon 8 Gen 3", "ram": "12GB", "camera": "13MP + 8MP", "screen": "14.6 inch Dynamic AMOLED 2X 120Hz", "battery": "11200mAh 45W", "storage": "256GB", "camera_front": "12MP", "connectivity": "5G, WiFi 6E"}', true, 5, 2, NULL, '2025-10-13 05:16:28.422406');
INSERT INTO public.products VALUES (168, 'iPad Pro M4 11 inch 5G 256GB', 'iPad Pro M4 11 inch - Chip M4, màn Ultra Retina XDR, kết nối 5G, hiệu năng vượt trội.', 32390000.00, 20, 'https://cdn.tgdd.vn/Products/Images/522/325529/ipad-pro-11-inch-m4-lte-black-thumb-600x600.jpg', '{"os": "iPadOS 18", "cpu": "Apple M4", "ram": "8GB", "screen": "11 inch Ultra Retina XDR", "battery": "9 giờ", "storage": "256GB", "connectivity": "5G, WiFi 6E"}', true, 5, 1, NULL, '2025-10-13 05:16:43.538571');
INSERT INTO public.products VALUES (169, 'Samsung Galaxy Tab S10 Ultra 5G 12GB/256GB', 'Galaxy Tab S10 Ultra - Màn AMOLED 14.6 inch, Snapdragon 8 Gen 2, S-Pen hỗ trợ, hiệu năng mạnh.', 29380000.00, 22, 'https://cdn.tgdd.vn/Products/Images/522/322132/samsung-galaxy-tab-s10-ultra-gray-thumb-600x600.jpg', '{"os": "Android 14, One UI 6.1", "cpu": "Snapdragon 8 Gen 2", "ram": "12GB", "camera": "13MP + 8MP", "screen": "14.6 inch Dynamic AMOLED 2X 120Hz", "battery": "11200mAh 45W", "storage": "256GB", "camera_front": "12MP"}', true, 5, 2, NULL, '2025-10-13 05:16:59.980669');
INSERT INTO public.products VALUES (170, 'iPad Pro M4 11 inch WiFi 256GB', 'iPad Pro M4 11 inch WiFi - Chip M4, màn Ultra Retina XDR, nhẹ chỉ 0.97kg.', 27290000.00, 25, 'https://cdn.tgdd.vn/Products/Images/522/325513/ipad-pro-11-inch-m4-wifi-black-thumb-600x600.jpg', '{"os": "iPadOS 18", "cpu": "Apple M4", "ram": "8GB", "screen": "11 inch Ultra Retina XDR", "battery": "9 giờ", "storage": "256GB", "connectivity": "WiFi 6E"}', true, 5, 1, NULL, '2025-10-13 05:17:17.469902');
INSERT INTO public.products VALUES (171, 'iPad Air 6 M2 11 inch 5G 1TB', 'iPad Air 6 M2 11 inch - Chip M2 mạnh mẽ, kết nối 5G, màn Retina IPS LCD sắc nét.', 26890000.00, 15, 'https://cdn.tgdd.vn/Products/Images/522/325524/ipad-air-11-inch-m2-lte-purple-thumb-600x600.jpg', '{"os": "iPadOS 18", "cpu": "Apple M2", "ram": "8GB", "screen": "11 inch Retina IPS LCD", "battery": "10 giờ", "storage": "1TB", "connectivity": "5G, WiFi 6"}', true, 5, 1, NULL, '2025-10-13 05:17:37.014965');
INSERT INTO public.products VALUES (172, 'iPad Air M3 13 inch 5G 128GB', 'iPad Air M3 13 inch 5G - Chip M3, màn Retina IPS LCD, pin lớn, hiệu năng mạnh mẽ.', 25990000.00, 18, 'https://cdn.tgdd.vn/Products/Images/522/335279/ipad-air-m3-13-inch-5g-purple-thumb-600x600.jpg', '{"os": "iPadOS 18", "cpu": "Apple M3", "ram": "8GB", "screen": "13 inch Retina IPS LCD", "battery": "10 giờ", "storage": "128GB", "connectivity": "5G"}', true, 5, 1, NULL, '2025-10-13 05:17:52.21421');
INSERT INTO public.products VALUES (173, 'Samsung Galaxy Tab S11 5G 12GB/128GB', 'Galaxy Tab S11 - Màn 11 inch AMOLED, Snapdragon 8 Gen 3, bút S-Pen, 5G tốc độ cao.', 23990000.00, 20, 'https://cdn.tgdd.vn/Products/Images/522/344721/samsung-galaxy-tab-s11-gray-thumb-600x600.jpg', '{"os": "Android 15, One UI 7", "cpu": "Snapdragon 8 Gen 3", "ram": "12GB", "camera": "13MP", "screen": "11 inch Dynamic AMOLED 2X 120Hz", "battery": "8400mAh 45W", "storage": "128GB", "camera_front": "8MP"}', true, 5, 2, NULL, '2025-10-13 05:18:07.740986');
INSERT INTO public.products VALUES (174, 'iPad Air M3 13 inch WiFi 128GB', 'iPad Air M3 13 inch WiFi - Chip M3, màn Retina IPS LCD, pin 10 tiếng, thiết kế mỏng nhẹ.', 22090000.00, 25, 'https://cdn.tgdd.vn/Products/Images/522/335275/ipad-air-m3-13-inch-wifi-purple-thumb-600x600.jpg', '{"os": "iPadOS 18", "cpu": "Apple M3", "ram": "8GB", "screen": "13 inch Retina IPS LCD", "battery": "10 giờ", "storage": "128GB", "connectivity": "WiFi 6"}', true, 5, 1, NULL, '2025-10-13 05:18:48.800509');
INSERT INTO public.products VALUES (175, 'Samsung Galaxy Watch7 LTE 40mm dây silicone', 'Galaxy Watch7 LTE - hỗ trợ eSIM, pin 1.7 ngày, sạc nhanh 12 giây, cảm biến sức khỏe AI.', 5830000.00, 30, 'https://cdn.tgdd.vn/Products/Images/7077/327696/samsung-galaxy-watch7-lte-40mm-tn2-600x600.jpg', '{"os": "Wear OS (One UI Watch 6)", "screen": "1.3 inch Super AMOLED", "battery": "300mAh (1.7 ngày)", "features": ["eSIM", "Cảm biến nhịp tim", "ECG", "Đo SpO2"], "material": "Silicone", "connectivity": "LTE, Bluetooth 5.3, WiFi"}', true, 4, 2, NULL, '2025-10-13 05:41:58.00702');
INSERT INTO public.products VALUES (176, 'Xiaomi Watch S4 47mm dây silicone', 'Xiaomi Watch S4 - Màn AMOLED 1.43 inch, pin 15 ngày, khung nhôm, hỗ trợ nghe gọi.', 3910000.00, 35, 'https://cdn.tgdd.vn/Products/Images/7077/335516/xiaomi-watch-s4-den-tn-600x600.jpg', '{"os": "Xiaomi HyperOS", "screen": "1.43 inch AMOLED 466x466", "battery": "470mAh (15 ngày)", "features": ["Nghe gọi", "Theo dõi giấc ngủ", "GPS độc lập"], "material": "Silicone", "connectivity": "Bluetooth 5.3"}', true, 4, 3, NULL, '2025-10-13 05:46:00.41253');
INSERT INTO public.products VALUES (178, 'Xiaomi Watch S4 47mm dây silicone (phiên bản đen)', 'Xiaomi Watch S4 - khung nhôm, pin 15 ngày, nghe gọi, hỗ trợ thể thao và sức khỏe.', 3610000.00, 40, 'https://cdn.tgdd.vn/Products/Images/7077/334435/xiaomi-watch-s4-den-tron-tn-600x600.jpg', '{"os": "Xiaomi HyperOS", "screen": "1.43 inch AMOLED", "battery": "15 ngày", "features": ["Nghe gọi", "Theo dõi thể thao", "Chống nước 5ATM"], "material": "Silicone", "connectivity": "Bluetooth 5.3"}', true, 4, 3, NULL, '2025-10-13 05:46:18.912152');
INSERT INTO public.products VALUES (179, 'Xiaomi Redmi Watch 4 47.5mm dây silicone', 'Redmi Watch 4 - màn AMOLED 1.97 inch, pin 20 ngày, khung nhôm, dây silicone, hỗ trợ SpO2.', 1640000.00, 50, 'https://cdn.tgdd.vn/Products/Images/7077/320842/dong-ho-thong-minh-xiaomi-redmi-watch-4-47-5mm-day-silicone-090124-114101-1-600x600.jpg', '{"os": "MiOS", "screen": "1.97 inch AMOLED 600 nits", "battery": "470mAh (20 ngày)", "features": ["Theo dõi sức khỏe", "GPS độc lập"], "material": "Silicone", "connectivity": "Bluetooth 5.3"}', true, 4, 3, NULL, '2025-10-13 05:46:34.659842');
INSERT INTO public.products VALUES (189, 'Samsung Galaxy Watch8 Classic 46mm dây da', 'Galaxy Watch8 Classic 46mm - thiết kế cổ điển, vòng xoay vật lý, pin 2 ngày, hỗ trợ nghe gọi.', 10790000.00, 20, 'https://cdn.tgdd.vn/Products/Images/7077/338266/samsung-galaxy-watch8-classic-trang-tn-600x600.jpg', '{"os": "Wear OS (One UI Watch 6)", "screen": "1.47 inch Super AMOLED", "battery": "590mAh (2 ngày)", "features": ["Theo dõi sức khỏe", "GPS", "Nghe gọi"], "material": "Thép + Dây da", "connectivity": "Bluetooth 5.3, WiFi"}', true, 4, 2, NULL, '2025-10-13 05:49:52.928367');
INSERT INTO public.products VALUES (190, 'Samsung Galaxy Watch8 LTE 40mm dây silicone', 'Galaxy Watch8 LTE 40mm - chip Exynos W1000, hỗ trợ eSIM, pin 1.7 ngày, cảm biến AI.', 7990000.00, 25, 'https://cdn.tgdd.vn/Products/Images/7077/340066/samsung-galaxy-watch8-lte-40mm-trang-tn-600x600.jpg', '{"os": "Wear OS (One UI Watch 6)", "screen": "1.34 inch Super AMOLED", "battery": "300mAh", "features": ["eSIM", "GPS", "Đo SpO2", "Nhịp tim"], "material": "Silicone", "connectivity": "LTE, Bluetooth, WiFi"}', true, 4, 2, NULL, '2025-10-13 05:50:06.784354');
INSERT INTO public.products VALUES (191, 'Samsung Galaxy Watch Ultra LTE 47mm (2025) dây silicone', 'Galaxy Watch Ultra 47mm - khung Titanium siêu bền, pin 3 ngày, chip Exynos W1000, eSIM.', 13990000.00, 15, 'https://cdn.tgdd.vn/Products/Images/7077/338267/galaxy-watch-ultra-2025-xanh-tn-600x600.jpg', '{"os": "Wear OS (One UI Watch 6)", "screen": "1.5 inch Super AMOLED", "battery": "590mAh (3 ngày)", "features": ["GPS", "eSIM", "Chống nước 10ATM"], "material": "Titanium + Silicone", "connectivity": "LTE, Bluetooth 5.3"}', true, 4, 2, NULL, '2025-10-13 05:50:18.523202');
INSERT INTO public.products VALUES (192, 'Samsung Galaxy Fit3 dây silicone', 'Galaxy Fit3 - màn AMOLED 1.6 inch, pin 13 ngày, chống nước 5ATM, theo dõi bước chân và giấc ngủ.', 890000.00, 50, 'https://cdn.tgdd.vn/Products/Images/7077/321616/samsung-galaxy-fit3-hong-thumb-1-600x600.jpg', '{"os": "Fit OS", "screen": "1.6 inch AMOLED", "battery": "208mAh (13 ngày)", "features": ["Theo dõi giấc ngủ", "Đếm bước chân", "Thông báo cuộc gọi"], "material": "Silicone", "connectivity": "Bluetooth 5.3"}', true, 4, 2, NULL, '2025-10-13 05:50:33.930256');
INSERT INTO public.products VALUES (193, 'Samsung Galaxy Watch7 44mm dây silicone', 'Galaxy Watch7 44mm - màn AMOLED 1.47 inch, pin 1.7 ngày, sạc nhanh 12 giây, AI Health tracking.', 4840000.00, 40, 'https://cdn.tgdd.vn/Products/Images/7077/327697/samsung-galaxy-watch7-44mm-bac-tn2-600x600.jpg', '{"os": "Wear OS (One UI Watch 5)", "screen": "1.47 inch Super AMOLED", "battery": "425mAh", "features": ["Theo dõi sức khỏe", "Đo SpO2", "Chống nước 5ATM"], "material": "Silicone", "connectivity": "Bluetooth 5.3"}', true, 4, 2, NULL, '2025-10-13 05:50:47.452336');
INSERT INTO public.products VALUES (194, 'Samsung Galaxy Watch7 40mm dây silicone', 'Galaxy Watch7 40mm - chip Exynos W930, màn 1.3 inch AMOLED, pin 1.5 ngày, hỗ trợ AI Health.', 4450000.00, 50, 'https://cdn.tgdd.vn/Products/Images/7077/327692/samsung-galaxy-watch7-40mm-kem-tn2-600x600.jpg', '{"os": "Wear OS (One UI Watch 5)", "screen": "1.3 inch Super AMOLED", "battery": "300mAh", "features": ["Theo dõi sức khỏe", "Chống nước 5ATM"], "material": "Silicone", "connectivity": "Bluetooth 5.3"}', true, 4, 2, NULL, '2025-10-13 05:51:02.841295');
INSERT INTO public.products VALUES (182, 'Xiaomi Watch 2 47.8mm dây silicone', 'Xiaomi Watch 2 - pin 65 giờ, chip Snapdragon W5+, hỗ trợ Wear OS và Google Assistant.', 2900000.00, 30, 'https://cdn.tgdd.vn/Products/Images/7077/321818/xiaomi-watch-2-bac-tn-600x600.jpg', '{"os": "Wear OS", "screen": "1.43 inch AMOLED 466x466", "battery": "65 giờ", "features": ["Nghe gọi", "Google Pay", "GPS"], "material": "Silicone", "connectivity": "Bluetooth 5.3, WiFi"}', true, 4, 3, NULL, '2025-10-13 05:47:48.514936');
INSERT INTO public.products VALUES (185, 'Samsung Galaxy Watch8 40mm dây silicone', 'Galaxy Watch8 40mm - Màn AMOLED 1.34 inch, pin 1.5 ngày, cảm biến sức khỏe AI, khung nhôm.', 6990000.00, 30, 'https://cdn.tgdd.vn/Products/Images/7077/338265/samsung-galaxy-watch8-40mm-trang-tb-600x600.jpg', '{"os": "Wear OS (One UI Watch 6)", "screen": "1.34 inch Super AMOLED", "battery": "300mAh (1.5 ngày)", "features": ["Đo SpO2", "Theo dõi nhịp tim", "Chống nước 5ATM"], "material": "Silicone", "connectivity": "Bluetooth 5.3, WiFi"}', true, 4, 2, NULL, '2025-10-13 05:48:55.575775');
INSERT INTO public.products VALUES (186, 'Samsung Galaxy Watch8 Classic LTE 46mm dây da', 'Galaxy Watch8 Classic LTE - Vòng xoay vật lý cổ điển, pin 2 ngày, nghe gọi qua eSIM.', 11990000.00, 20, 'https://cdn.tgdd.vn/Products/Images/7077/340065/samsung-galaxy-watch8-classic-lte-46mm-den-tn-600x600.jpg', '{"os": "Wear OS (One UI Watch 6)", "screen": "1.5 inch Super AMOLED", "battery": "590mAh (2 ngày)", "features": ["eSIM", "Đo SpO2", "Theo dõi giấc ngủ"], "material": "Thép không gỉ + Dây da", "connectivity": "LTE, Bluetooth, WiFi"}', true, 4, 2, NULL, '2025-10-13 05:49:13.127507');
INSERT INTO public.products VALUES (187, 'Samsung Galaxy Watch8 44mm dây silicone', 'Galaxy Watch8 44mm - màn Super AMOLED 1.47 inch, pin 1.7 ngày, hỗ trợ sạc nhanh.', 7990000.00, 35, 'https://cdn.tgdd.vn/Products/Images/7077/340068/samsung-galaxy-watch8-44mm-den-tb-600x600.jpg', '{"os": "Wear OS (One UI Watch 6)", "screen": "1.47 inch Super AMOLED", "battery": "425mAh (1.7 ngày)", "features": ["Theo dõi sức khỏe", "SpO2", "GPS"], "material": "Silicone", "connectivity": "Bluetooth 5.3, WiFi"}', true, 4, 2, NULL, '2025-10-13 05:49:25.441269');
INSERT INTO public.products VALUES (188, 'Samsung Galaxy Watch8 LTE 44mm dây silicone', 'Galaxy Watch8 LTE - hỗ trợ eSIM, pin 1.7 ngày, đo nhịp tim, SpO2, theo dõi stress và thể thao.', 8990000.00, 25, 'https://cdn.tgdd.vn/Products/Images/7077/340067/samsung-galaxy-watch8-lte-44mm-den-tn-600x600.jpg', '{"os": "Wear OS (One UI Watch 6)", "screen": "1.47 inch Super AMOLED", "battery": "425mAh (1.7 ngày)", "features": ["eSIM", "Theo dõi sức khỏe", "Chống nước 5ATM"], "material": "Silicone", "connectivity": "LTE, Bluetooth 5.3"}', true, 4, 2, NULL, '2025-10-13 05:49:39.634242');
INSERT INTO public.products VALUES (205, 'Bộ Bàn Phím Chuột Bluetooth Logitech MK250', 'Bộ combo Logitech MK250 gồm bàn phím và chuột Bluetooth, kết nối không dây ổn định, phù hợp học tập và văn phòng.', 590000.00, 50, 'https://cdnv2.tgdd.vn/mwg-static/tgdd/Products/Images/4547/341121/bo-ban-phim-chuot-bluetooth-logitech-mk250-210725-042039-677-600x600.jpg', '{"color": ["Đen", "Trắng"], "layout": "Fullsize 104 phím", "battery": "18 tháng", "features": ["Combo bàn phím + chuột", "Silent Key", "Thiết kế mỏng nhẹ"], "connection": "Bluetooth, USB Receiver"}', true, 3, 16, NULL, '2025-10-13 05:51:26.61399');
INSERT INTO public.products VALUES (206, 'Bàn Phím Có Dây Gaming Asus TUF K1', 'Asus TUF K1 - bàn phím gaming có dây, switch giả cơ cảm giác bấm tốt, đèn RGB nhiều vùng, thiết kế chống tràn.', 870000.00, 40, 'https://cdn.tgdd.vn/Products/Images/4547/279466/co-day-gaming-asus-tuf-k1-thumb-600x600.jpeg', '{"layout": "Fullsize 104 phím", "switch": "Mem-Mechanical", "features": ["Chống tràn", "Phím media riêng", "Kê tay tháo rời"], "lighting": "RGB đa vùng", "connection": "USB có dây"}', true, 3, 10, NULL, '2025-10-13 05:51:45.507639');
INSERT INTO public.products VALUES (207, 'Bàn Phím Cơ Bluetooth Akko 5075B Plus Black & Gold', 'Akko 5075B Plus - bàn phím cơ Bluetooth thiết kế cao cấp, hỗ trợ 3 mode (Bluetooth/2.4G/USB), LED RGB.', 1280000.00, 35, 'https://cdnv2.tgdd.vn/mwg-static/tgdd/Products/Images/4547/342558/ban-phim-co-bluetooth-akko-5075b-plus-black-gold-thumb-638911148777788277-600x600.jpg', '{"layout": "75%", "switch": "Akko CS Jelly Pink", "battery": "Pin sạc USB-C", "features": ["Hot-swap", "Keycap PBT", "3 mode kết nối"], "lighting": "RGB", "connection": "Bluetooth, 2.4GHz, USB Receiver"}', true, 3, 17, NULL, '2025-10-13 05:52:06.949471');
INSERT INTO public.products VALUES (208, 'Bàn Phím Cơ Bluetooth Akko TAC87 Prunus Lannesiana', 'Akko TAC87 Prunus Lannesiana - bàn phím cơ Bluetooth thiết kế hồng trắng nổi bật, keycap PBT in dye-sub bền màu.', 1250000.00, 40, 'https://cdnv2.tgdd.vn/mwg-static/tgdd/Products/Images/4547/351690/ban-phim-co-bluetooth-akko-tac87-prunus-lannesiana-stellar-rose-thumb-638937233092479298-600x600.jpg', '{"layout": "TKL (87 phím)", "switch": "Akko CS Jelly White", "features": ["Kết nối 2 mode", "Keycap PBT", "Thiết kế retro nữ tính"], "lighting": "LED trắng", "connection": "Bluetooth, USB Type-C"}', true, 3, 17, NULL, '2025-10-13 05:52:20.565177');
INSERT INTO public.products VALUES (196, 'Chuột Không dây Gaming Logitech G304', 'Logitech G304 - Chuột gaming không dây siêu nhẹ, cảm biến HERO 12K DPI, pin lên đến 250 giờ.', 725000.00, 45, 'https://cdn.tgdd.vn/Products/Images/86/326724/chuot-bluetooth-gaming-logitech-g304-den-600x600.jpg', '{"dpi": "12000 DPI", "color": ["Đen", "Trắng"], "range": "10 mét", "battery": "1 pin AA (250 giờ)", "features": ["Cảm biến HERO", "Tốc độ phản hồi 1ms", "Độ bền cao"], "connection": "USB Receiver (Lightspeed Wireless)"}', true, 3, 16, NULL, '2025-10-13 05:38:55.691828');
INSERT INTO public.products VALUES (197, 'Chuột sạc Bluetooth Silent Logitech MX Anywhere 3s', 'Logitech MX Anywhere 3s - Chuột không dây cao cấp, sạc nhanh USB-C, DPI 8000, hỗ trợ Bluetooth và USB Receiver.', 1550000.00, 35, 'https://cdn.tgdd.vn/Products/Images/86/326657/chuot-bluetooth-silent-logitech-mx-anywhere-3s-hong-600x600.jpg', '{"dpi": "8000 DPI", "color": ["Hồng", "Xám", "Đen"], "battery": "Pin sạc (70 ngày)", "charging": "USB-C", "features": ["Silent Click", "Cuộn siêu nhanh MagSpeed", "Kết nối đa thiết bị"], "connection": "USB Receiver, Bluetooth"}', true, 3, 16, NULL, '2025-10-13 05:39:12.22587');
INSERT INTO public.products VALUES (198, 'Chuột Có dây Gaming Logitech G502 Hero', 'Logitech G502 Hero - Chuột gaming có dây huyền thoại, cảm biến HERO 25K DPI, đèn RGB tùy chỉnh.', 920000.00, 50, 'https://cdnv2.tgdd.vn/mwg-static/tgdd/Products/Images/86/326725/chuot-co-day-gaming-logitech-g502-hero-240125-080633-229-600x600.jpg', '{"dpi": "25600 DPI", "features": ["Đèn RGB", "Trọng lượng điều chỉnh", "11 nút lập trình"], "material": "Nhựa ABS", "connection": "USB có dây", "cable_length": "1.83 mét"}', true, 3, 16, NULL, '2025-10-13 05:39:28.190521');
INSERT INTO public.products VALUES (199, 'Chuột Bluetooth Silent Logitech M240', 'Logitech M240 - Chuột Bluetooth yên tĩnh, pin 18 tháng, thiết kế nhỏ gọn tiện lợi, phù hợp học tập và văn phòng.', 320000.00, 80, 'https://cdn.tgdd.vn/Products/Images/86/311113/chuot-bluetooth-silent-logitech-m240-thumb-600x600.jpg', '{"dpi": "4000 DPI", "color": ["Hồng", "Trắng", "Đen"], "range": "10 mét", "battery": "1 pin AA (18 tháng)", "features": ["Silent Click", "Nhỏ gọn", "3 màu"], "connection": "Bluetooth 5.1"}', true, 3, 16, NULL, '2025-10-13 05:40:59.648448');
INSERT INTO public.products VALUES (209, 'Bàn Phím Cơ Có Dây Akko FUN60 Pro SP Black Akko CS Switch', 'Akko FUN60 Pro SP - bàn phím cơ mini 60%, dây Type-C, LED RGB, switch Akko CS cực mượt.', 640000.00, 45, 'https://cdnv2.tgdd.vn/mwg-static/tgdd/Products/Images/4547/342572/ban-phim-co-co-day-akko-fun60-pro-sp-black-thumb-638912839681085669-600x600.jpg', '{"layout": "60%", "switch": "Akko CS Silver", "features": ["Hot-swap", "Dây rời", "Thiết kế nhỏ gọn"], "lighting": "RGB", "connection": "USB Type-C (có dây)"}', true, 3, 17, NULL, '2025-10-13 05:52:43.052053');
INSERT INTO public.products VALUES (210, 'Màn hình MSI MAG 274F 27 inch FHD 200Hz Rapid IPS', 'Màn hình gaming MSI MAG 274F kích thước 27 inch, tần số quét 200Hz, tấm nền Rapid IPS cho độ phản hồi chỉ 0.5ms, hỗ trợ HDR Ready.', 3690000.00, 25, 'https://lh3.googleusercontent.com/-VxDjg44l0n_atz51Ks4AtlerQanNDQpHz1aB1U6h_jYwIrP7Ee395bVNk0wI8cqk0UMsdlTUrriebwTtEYFVtZxTNLYr4s2=w230-rw', '{"panel": "Rapid IPS", "ports": ["HDMI", "DisplayPort"], "features": ["HDR Ready", "Gaming mode", "Anti-Flicker"], "resolution": "1920x1080 (Full HD)", "screen_size": "27 inch", "refresh_rate": "200Hz", "response_time": "0.5ms"}', true, 7, 14, NULL, '2025-10-13 05:53:08.490864');
INSERT INTO public.products VALUES (211, 'Màn hình MSI MAG 276CF E20 27 inch FHD 200Hz VA', 'MSI MAG 276CF E20 - màn hình cong 27 inch, tấm nền VA, tần số quét 200Hz, thời gian phản hồi 0.5ms, thiết kế viền mỏng.', 3690000.00, 25, 'https://lh3.googleusercontent.com/nkCJXz4XFs7EiN2ulvAYJsj0rK8CmJ2eDub2nuGAXxLhcbssvOIUKDG5U9gClddpznkGhRe37hXM4TfvPQISLJg68dl5GZGolg=w230-rw', '{"panel": "VA", "ports": ["HDMI", "DisplayPort"], "features": ["Cong 1500R", "Night Vision", "AMD FreeSync"], "resolution": "1920x1080 (Full HD)", "screen_size": "27 inch (Cong)", "refresh_rate": "200Hz", "response_time": "0.5ms"}', true, 7, 14, NULL, '2025-10-13 05:53:27.715601');
INSERT INTO public.products VALUES (216, 'Màn hình Dell SE2725HM 27 inch FHD 100Hz IPS', 'Dell SE2725HM - màn hình văn phòng 27 inch, tấm nền IPS, 100Hz, thiết kế viền mỏng hiện đại, tiết kiệm điện năng.', 3650000.00, 25, 'https://lh3.googleusercontent.com/HOppkeULy-u1vCw6Ox6n6kvuuAVdMnidTmUVA5mGXahgkees10ZxT4tJWKi9g2VwGx6u-H-bSzouUcrnM0huFBVSOutD8Ew=w230-rw', '{"panel": "IPS", "ports": ["HDMI", "VGA"], "features": ["Low Blue Light", "ComfortView", "Tilt Adjustment"], "resolution": "1920x1080 (Full HD)", "screen_size": "27 inch", "refresh_rate": "100Hz", "response_time": "5ms"}', true, 7, 13, NULL, '2025-10-13 05:37:03.186706');
INSERT INTO public.products VALUES (214, 'Màn hình Asus VY279HF 27 inch FHD 100Hz IPS', 'Màn hình Asus VY279HF - kích thước 27 inch, tấm nền IPS, tần số quét 100Hz, hỗ trợ công nghệ Eye Care bảo vệ mắt.', 3650000.00, 30, 'https://lh3.googleusercontent.com/qNsxVfy1x7GYrdneLWE-UjZMJ75E_cEAPGJEF-WOzMtSER4FOUO884otHYaq_d_IHJWA4NyZIKcI7HBQFtXrmf1DX9zvoKDyqA=w230-rw', '{"panel": "IPS", "ports": ["HDMI", "VGA"], "features": ["Asus Eye Care", "Chống nháy", "Low Blue Light"], "resolution": "1920x1080 (Full HD)", "screen_size": "27 inch", "refresh_rate": "100Hz", "response_time": "1ms"}', true, 7, 10, NULL, '2025-10-13 05:37:19.923427');
INSERT INTO public.products VALUES (215, 'Màn hình Acer Nitro KG270 X1 27 inch FHD 200Hz IPS', 'Acer Nitro KG270 X1 - màn hình chơi game 27 inch, Full HD, IPS, tần số quét 200Hz, phản hồi 1ms, thiết kế viền mỏng sang trọng.', 3650000.00, 20, 'https://lh3.googleusercontent.com/u5V3-Auj0ljMvzO6lEkf45Z0gQXK9iS6DE6hOJXSw0HfWlxAiaB6U2nA3F5vMKBHdbIVWM9HnmrgqflCgJHVVnxzaBLiotxWxQ=w230-rw', '{"panel": "IPS", "ports": ["HDMI", "DisplayPort"], "features": ["AMD FreeSync", "Flicker-less", "ZeroFrame"], "resolution": "1920x1080 (Full HD)", "screen_size": "27 inch", "refresh_rate": "200Hz", "response_time": "1ms"}', true, 7, 15, NULL, '2025-10-13 05:37:37.407692');
INSERT INTO public.products VALUES (195, 'Chuột Bluetooth Silent Logitech Signature M650', 'Chuột Logitech M650 - Chuột Bluetooth không dây yên tĩnh, thiết kế công thái học, pin 24 tháng.', 610000.00, 60, 'https://cdn.tgdd.vn/Products/Images/86/299931/chuot-khong-day-logitech-silent-signature-m650-size-m-300523-084259-600x600.jpg', '{"dpi": "4000 DPI", "range": "10 mét", "battery": "1 pin AA (24 tháng)", "features": ["Silent Click", "Cảm giác bấm êm", "Đa màu"], "material": "Nhựa cao cấp", "connection": "Bluetooth, USB Receiver"}', true, 3, 16, NULL, '2025-10-13 05:38:38.891805');
INSERT INTO public.products VALUES (180, 'Apple Watch Ultra 2 GPS + Cellular 49mm viền Titanium dây vải', 'Apple Watch Ultra 2 - chip S9, định vị GPS kép, chống nước 100m, khung Titanium siêu bền.', 21890000.00, 10, 'https://cdn.tgdd.vn/Products/Images/7077/329719/apple-watch-ultra-2-gps-cellular-49mm-vien-titanium-day-alpine-xanh-den-600x600.png', '{"os": "watchOS 10", "screen": "1.92 inch OLED LTPO", "battery": "36 giờ", "features": ["GPS kép", "Chống nước 100m", "Đo SpO2"], "material": "Titanium", "connectivity": "GPS, Cellular, Bluetooth 5.3"}', true, 4, 1, NULL, '2025-10-13 05:47:29.855381');
INSERT INTO public.products VALUES (212, 'Màn hình MSI MAG 272F X24 27 inch FHD 240Hz Rapid IPS', 'MSI MAG 272F X24 - màn hình gaming cao cấp 27 inch, 240Hz, Rapid IPS, độ trễ 0.5ms, hỗ trợ NVIDIA G-Sync Compatible.', 3690000.00, 20, 'https://lh3.googleusercontent.com/dM4n-YxkVNFTTycUh7uUeyFybRsU5FpXznQhCEih2QdCPDSoz27aLKc8IxPWcJBkkQlogFXO9WndqU-HWEJr7eWP0jhXr61jqg=w230-rw', '{"panel": "Rapid IPS", "ports": ["HDMI", "DisplayPort"], "features": ["G-Sync Compatible", "Game Mode", "Anti-Flicker"], "resolution": "1920x1080 (Full HD)", "screen_size": "27 inch", "refresh_rate": "240Hz", "response_time": "0.5ms"}', true, 7, 14, NULL, '2025-10-13 05:53:50.771018');
INSERT INTO public.products VALUES (213, 'Màn hình MSI G2412 23.8 inch FHD 170Hz IPS', 'Màn hình MSI G2412 kích thước 23.8 inch, tấm nền IPS, độ phân giải Full HD, tần số quét 170Hz, thời gian phản hồi 1ms.', 3650000.00, 30, 'https://lh3.googleusercontent.com/xo6g1StVzIqwMWdbThC4l-QYLy4V-S-wxwcTY1cmtJjyUGqOqo9O7qL2-SA4QJ97O7etIlBS4OiO48IukcbAmjIN7FFTodiAwA=w230-rw', '{"panel": "IPS", "ports": ["HDMI", "DisplayPort"], "features": ["Anti-Flicker", "Low Blue Light", "Gaming OSD"], "resolution": "1920x1080 (Full HD)", "screen_size": "23.8 inch", "refresh_rate": "170Hz", "response_time": "1ms"}', true, 7, 14, NULL, '2025-10-13 05:54:12.09518');


--
-- TOC entry 4129 (class 0 OID 17018)
-- Dependencies: 242
-- Data for Name: review_likes; Type: TABLE DATA; Schema: public; Owner: utephonehub_user
--



--
-- TOC entry 4127 (class 0 OID 16996)
-- Dependencies: 240
-- Data for Name: reviews; Type: TABLE DATA; Schema: public; Owner: utephonehub_user
--

INSERT INTO public.reviews VALUES (1, 1, 61, 5, 'vip', '2025-10-13 04:33:14.989661', '2025-10-13 04:33:14.989661');
INSERT INTO public.reviews VALUES (2, 1, 68, 5, 'ổn', '2025-10-13 05:55:26.225965', '2025-10-13 05:55:26.225965');
INSERT INTO public.reviews VALUES (3, 6, 61, 1, 'test', '2025-10-13 06:13:25.653067', '2025-10-13 06:13:25.653068');
INSERT INTO public.reviews VALUES (4, 6, 81, 3, 'chua mua', '2025-10-13 06:14:32.373202', '2025-10-13 06:14:32.373203');
INSERT INTO public.reviews VALUES (5, 1, 62, 4, 'sản phẩm tốt', '2025-10-13 06:28:47.978081', '2025-10-13 06:28:47.978082');
INSERT INTO public.reviews VALUES (6, 19, 61, 4, 'tốt', '2025-10-13 17:10:47.853302', '2025-10-13 17:10:47.853302');
INSERT INTO public.reviews VALUES (7, 17, 61, 5, 'tốt', '2025-10-14 14:01:10.69256', '2025-10-14 14:01:10.69256');


--
-- TOC entry 4105 (class 0 OID 16812)
-- Dependencies: 218
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: utephonehub_user
--

INSERT INTO public.users VALUES (6, 'tuantujr', 'Nguyễn Tuấn Tú', 'ttgaming1999@gmail.com', '$2a$12$rs4Xa9XhuM.y1/2DmLxGlOs3sn5eXI/CTK4dvkhepWyJMsLemxERm', NULL, 'customer', 'active', '2025-10-12 23:33:16.355835', '2025-10-12 23:38:48.238043');
INSERT INTO public.users VALUES (1, 'admin', 'Administrator', 'tuantu.career@gmail.com', '$2a$12$lCYMdP.J3eBElOFRGKKeJuTxvm7ZWumbv16Hm2e7OJECpzRkAc2dy', '0901234567', 'admin', 'active', '2025-10-12 23:28:01.427102', '2025-10-13 02:44:48.388254');
INSERT INTO public.users VALUES (7, '23133061@student.hcmute.edu.vn', 'Phan Trong Qui ', '23133061@student.hcmute.edu.vn', '$2a$12$/PmRAkhQldvISQtI0IWDY.Cu2/bvdb6PVvHtI4/6VTF2Vp5sg3luW', NULL, 'customer', 'active', '2025-10-13 02:47:11.81194', '2025-10-13 02:47:11.811941');
INSERT INTO public.users VALUES (8, 'tu01699963463', 'Người Thích Đùa', 'tu01699963463@gmail.com', '$2a$12$IUBgHd3xU9rf7Ex3TT5PeOZ0RUE9lB1T2NKbeCRr0fqkMCjcw7R8y', NULL, 'customer', 'active', '2025-10-13 02:50:31.381818', '2025-10-13 02:50:31.381819');
INSERT INTO public.users VALUES (9, 'nguyengiakhang116', 'Nguyễn Gia Khang', 'nguyengiakhang116@gmail.com', '$2a$12$lypjIp5X853fFwC6ndgKle6Mi284qogSpQ9dmjmlfsRALoph/3.XG', NULL, 'customer', 'active', '2025-10-13 03:04:51.094677', '2025-10-13 03:36:39.667203');
INSERT INTO public.users VALUES (10, 'khang676', 'khang', 'giakhang0167@gmail.com', '$2a$12$AQAaCdhOoZRLa14ZpVScCeO1bqKrcP1lvczx28Bo0yszCMvYRpeG.', NULL, 'customer', 'active', '2025-10-13 03:20:28.080711', '2025-10-13 03:40:16.421908');
INSERT INTO public.users VALUES (12, 'luutrankimphu2021', 'Lưu Trần Kim Phú', 'luutrankimphu2021@gmail.com', '$2a$12$MaFgPcoxwjaX2KGOOFEGXuVyn/Nwo6chSDXK508pASFmzB2Wbx4im', NULL, 'customer', 'active', '2025-10-13 09:09:16.305329', '2025-10-13 09:09:16.30533');
INSERT INTO public.users VALUES (14, 'Nguyễn Lê Hoàng Kiệt', 'Nguyễn Lê Hoàng Kiệt', '23133071@student.hcmute.edu.vn', '$2a$12$nBZBzg2xj6oiAJKTIxo8de7oltIKrcOv3T6PkaRG2QKGklEx28Vy6', NULL, 'customer', 'active', '2025-10-13 13:42:30.586336', '2025-10-13 13:42:30.586337');
INSERT INTO public.users VALUES (15, 'nguyen', 'trinhvanguyen', 'trinhnguyen11012005@gmail.com', '$2a$12$mAzY9gG8878UH0VC4p5veOz6K.42Tz3eWvrfgk7U7E6HMDUFxnGky', NULL, 'customer', 'active', '2025-10-13 14:00:31.319197', '2025-10-13 14:00:31.319198');
INSERT INTO public.users VALUES (16, 'tranthanhdanhktvn2005d', 'Danh Trần', 'tranthanhdanhktvn2005d@gmail.com', '$2a$12$zWvCO5rTPT6eJr0Ib1LRjuVFoH55vZ4x4DeOpahVHvfm1ZbBRyJKa', NULL, 'customer', 'active', '2025-10-13 14:12:26.128798', '2025-10-13 14:12:26.1288');
INSERT INTO public.users VALUES (17, 'kienhung.do1105', 'Kiến Hưng Đỗ', 'kienhung.do1105@gmail.com', '$2a$12$S3KFB1xyS7.OYwprjdME8.NKlyv.s7AVud3U/I23BjkOR27QFAQba', '0357554576', 'customer', 'active', '2025-10-13 14:42:22.279085', '2025-10-13 15:45:14.9356');
INSERT INTO public.users VALUES (13, 'main', 'Trần Quốc Giăng', 'tranquocgiang136@gmail.com', '$2a$12$9VhTtmcWTK0QD8g3RVdCQ.Gxd9g88Ec6nMl/QLNoF7i.1QKjnu9Mu', '0896542145', 'customer', 'active', '2025-10-13 11:59:06.082996', '2025-10-13 16:34:11.022661');
INSERT INTO public.users VALUES (18, '23133055', 'Luu Tran Kim Phu', '23133055@student.hcmute.edu.vn', '$2a$12$GxflLtedPwzhZ7eMErSGp.HqaIABeQnKCnThvoRtNqbJDzKP4Xo0K', NULL, 'customer', 'active', '2025-10-13 16:50:07.296891', '2025-10-13 16:50:07.296892');
INSERT INTO public.users VALUES (22, 'chungthiainguyen.56228', 'Nguyên Ái', 'chungthiainguyen.56228@gmail.com', '$2a$12$j1YYcjG19XYTYIWsAPp0EuPCGYdsYj3MrGkHoCOP6RgSbmaB5AbY.', NULL, 'customer', 'active', '2025-10-14 01:40:39.808623', '2025-10-14 01:40:39.808624');
INSERT INTO public.users VALUES (19, 'huy', 'Huỳnh Hữu Huy', '23133027@student.hcmute.edu.vn', '$2a$12$fq2/wKazE6NQiDPRa86oy.HT8qQdncL2hWBlM3X4P662Tymk9wWJi', NULL, 'customer', 'active', '2025-10-13 17:10:04.058636', '2025-10-13 20:43:42.853671');
INSERT INTO public.users VALUES (20, 'phu', 'Phan Phu', 'phantrongphu22072005.thptpvt@gmail.com', '$2a$12$RdhVXjlEGIfv9qf6CNo/POBpCVDX6GnEemWm9KCPhWhuWi.fYRAx6', NULL, 'customer', 'active', '2025-10-13 20:45:08.7662', '2025-10-14 01:43:38.905467');
INSERT INTO public.users VALUES (21, '23133030', 'Do Kien Hung', '23133030@student.hcmute.edu.vn', '$2a$12$4mxJtEvNmuMEzIIEYlfiQOVdGxnjC/7ENLdJwMESBdYf7dtWSlp2y', NULL, 'customer', 'active', '2025-10-14 01:07:19.72767', '2025-10-14 02:08:29.548609');
INSERT INTO public.users VALUES (24, 'test123', 'Test', 'kienhung.do11052@gmail.com', '$2a$12$6Bf8mE3eD0jTBF.6zRxouu59.66pEq8xJgz08YIOYkKYItXuV1AP6', NULL, 'customer', 'locked', '2025-10-14 02:05:01.305307', '2025-10-14 02:14:35.391021');
INSERT INTO public.users VALUES (23, 'ttt', 'ttt', 'tt@gmail.com', '$2a$12$UUSl9zuTrsGdlc85bU/ITORXcfYDSJhuzPHIq511OGS7qMfut5YR.', NULL, 'customer', 'locked', '2025-10-14 01:41:47.770847', '2025-10-14 02:15:12.756101');
INSERT INTO public.users VALUES (11, 'quangduyreal', 'Nguyễn Văn Quang Duy', 'fansjaki@gmail.com', '$2a$12$6miDKAcUJOMNkCxaZJoWseTm89Mk9r14Gb9WKgzQNH.uxR55XWvJi', '0347903380', 'customer', 'locked', '2025-10-13 04:01:11.687716', '2025-10-14 14:03:44.305558');
INSERT INTO public.users VALUES (25, '23162056', 'Van Duc Cong Minh', '23162056@student.hcmute.edu.vn', '$2a$12$FuetNxLPmP/9ZLEtZHKZeu2TAkMqWLc2G9vkg1SKN0/iTznYBqByC', NULL, 'customer', 'locked', '2025-10-14 04:02:48.075668', '2025-10-14 14:41:11.910833');


--
-- TOC entry 4117 (class 0 OID 16905)
-- Dependencies: 230
-- Data for Name: vouchers; Type: TABLE DATA; Schema: public; Owner: utephonehub_user
--

INSERT INTO public.vouchers VALUES (1, 'WELCOME10', 'PERCENTAGE', 10.00, 1000, 1000000.00, '2025-12-31 23:59:59', 'ACTIVE', '2025-10-12 23:28:01.824138', '2025-10-12 23:28:01.824138');
INSERT INTO public.vouchers VALUES (2, 'SALE50K', 'FIXED_AMOUNT', 50000.00, 500, 2000000.00, '2025-12-31 23:59:59', 'ACTIVE', '2025-10-12 23:28:01.824138', '2025-10-12 23:28:01.824138');
INSERT INTO public.vouchers VALUES (3, 'NEWUSER', 'PERCENTAGE', 15.00, 100, 500000.00, '2025-12-31 23:59:59', 'ACTIVE', '2025-10-12 23:28:01.824138', '2025-10-12 23:28:01.824138');
INSERT INTO public.vouchers VALUES (4, 'FLASH100K', 'FIXED_AMOUNT', 100000.00, 200, 5000000.00, '2025-11-30 23:59:59', 'ACTIVE', '2025-10-12 23:28:01.824138', '2025-10-12 23:28:01.824138');
INSERT INTO public.vouchers VALUES (5, 'VIP20', 'PERCENTAGE', 20.00, 50, 10000000.00, '2025-12-31 23:59:59', 'ACTIVE', '2025-10-12 23:28:01.824138', '2025-10-12 23:28:01.824138');
INSERT INTO public.vouchers VALUES (6, 'TEST', 'PERCENTAGE', 5.00, NULL, NULL, '2025-11-12 10:33:00', 'INACTIVE', '2025-10-13 03:34:18.123649', '2025-10-13 04:33:43.783791');
INSERT INTO public.vouchers VALUES (7, 'TUTRIAN', 'PERCENTAGE', 99.00, NULL, NULL, '2025-11-12 17:39:00', 'ACTIVE', '2025-10-13 10:40:14.235624', '2025-10-13 10:40:14.235625');
INSERT INTO public.vouchers VALUES (8, 'TESTVC', 'PERCENTAGE', 10.00, NULL, NULL, '2025-11-13 21:04:00', 'ACTIVE', '2025-10-14 14:06:00.560739', '2025-10-14 14:06:00.56074');


--
-- TOC entry 4151 (class 0 OID 0)
-- Dependencies: 219
-- Name: addresses_id_seq; Type: SEQUENCE SET; Schema: public; Owner: utephonehub_user
--

SELECT pg_catalog.setval('public.addresses_id_seq', 16, true);


--
-- TOC entry 4152 (class 0 OID 0)
-- Dependencies: 223
-- Name: brands_id_seq; Type: SEQUENCE SET; Schema: public; Owner: utephonehub_user
--

SELECT pg_catalog.setval('public.brands_id_seq', 18, true);


--
-- TOC entry 4153 (class 0 OID 0)
-- Dependencies: 237
-- Name: cart_items_id_seq; Type: SEQUENCE SET; Schema: public; Owner: utephonehub_user
--

SELECT pg_catalog.setval('public.cart_items_id_seq', 42, true);


--
-- TOC entry 4154 (class 0 OID 0)
-- Dependencies: 235
-- Name: carts_id_seq; Type: SEQUENCE SET; Schema: public; Owner: utephonehub_user
--

SELECT pg_catalog.setval('public.carts_id_seq', 105, true);


--
-- TOC entry 4155 (class 0 OID 0)
-- Dependencies: 221
-- Name: categories_id_seq; Type: SEQUENCE SET; Schema: public; Owner: utephonehub_user
--

SELECT pg_catalog.setval('public.categories_id_seq', 17, true);


--
-- TOC entry 4156 (class 0 OID 0)
-- Dependencies: 233
-- Name: order_items_id_seq; Type: SEQUENCE SET; Schema: public; Owner: utephonehub_user
--

SELECT pg_catalog.setval('public.order_items_id_seq', 22, true);


--
-- TOC entry 4157 (class 0 OID 0)
-- Dependencies: 231
-- Name: orders_id_seq; Type: SEQUENCE SET; Schema: public; Owner: utephonehub_user
--

SELECT pg_catalog.setval('public.orders_id_seq', 16, true);


--
-- TOC entry 4158 (class 0 OID 0)
-- Dependencies: 227
-- Name: product_images_id_seq; Type: SEQUENCE SET; Schema: public; Owner: utephonehub_user
--

SELECT pg_catalog.setval('public.product_images_id_seq', 1, false);


--
-- TOC entry 4159 (class 0 OID 0)
-- Dependencies: 225
-- Name: products_id_seq; Type: SEQUENCE SET; Schema: public; Owner: utephonehub_user
--

SELECT pg_catalog.setval('public.products_id_seq', 217, true);


--
-- TOC entry 4160 (class 0 OID 0)
-- Dependencies: 241
-- Name: review_likes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: utephonehub_user
--

SELECT pg_catalog.setval('public.review_likes_id_seq', 1, false);


--
-- TOC entry 4161 (class 0 OID 0)
-- Dependencies: 239
-- Name: reviews_id_seq; Type: SEQUENCE SET; Schema: public; Owner: utephonehub_user
--

SELECT pg_catalog.setval('public.reviews_id_seq', 7, true);


--
-- TOC entry 4162 (class 0 OID 0)
-- Dependencies: 217
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: utephonehub_user
--

SELECT pg_catalog.setval('public.users_id_seq', 25, true);


--
-- TOC entry 4163 (class 0 OID 0)
-- Dependencies: 229
-- Name: vouchers_id_seq; Type: SEQUENCE SET; Schema: public; Owner: utephonehub_user
--

SELECT pg_catalog.setval('public.vouchers_id_seq', 8, true);


--
-- TOC entry 3892 (class 2606 OID 16837)
-- Name: addresses addresses_pkey; Type: CONSTRAINT; Schema: public; Owner: utephonehub_user
--

ALTER TABLE ONLY public.addresses
    ADD CONSTRAINT addresses_pkey PRIMARY KEY (id);


--
-- TOC entry 3897 (class 2606 OID 16865)
-- Name: brands brands_pkey; Type: CONSTRAINT; Schema: public; Owner: utephonehub_user
--

ALTER TABLE ONLY public.brands
    ADD CONSTRAINT brands_pkey PRIMARY KEY (id);


--
-- TOC entry 3929 (class 2606 OID 16984)
-- Name: cart_items cart_items_cart_id_product_id_key; Type: CONSTRAINT; Schema: public; Owner: utephonehub_user
--

ALTER TABLE ONLY public.cart_items
    ADD CONSTRAINT cart_items_cart_id_product_id_key UNIQUE (cart_id, product_id);


--
-- TOC entry 3931 (class 2606 OID 16982)
-- Name: cart_items cart_items_pkey; Type: CONSTRAINT; Schema: public; Owner: utephonehub_user
--

ALTER TABLE ONLY public.cart_items
    ADD CONSTRAINT cart_items_pkey PRIMARY KEY (id);


--
-- TOC entry 3925 (class 2606 OID 16967)
-- Name: carts carts_pkey; Type: CONSTRAINT; Schema: public; Owner: utephonehub_user
--

ALTER TABLE ONLY public.carts
    ADD CONSTRAINT carts_pkey PRIMARY KEY (id);


--
-- TOC entry 3927 (class 2606 OID 16969)
-- Name: carts carts_user_id_key; Type: CONSTRAINT; Schema: public; Owner: utephonehub_user
--

ALTER TABLE ONLY public.carts
    ADD CONSTRAINT carts_user_id_key UNIQUE (user_id);


--
-- TOC entry 3895 (class 2606 OID 16851)
-- Name: categories categories_pkey; Type: CONSTRAINT; Schema: public; Owner: utephonehub_user
--

ALTER TABLE ONLY public.categories
    ADD CONSTRAINT categories_pkey PRIMARY KEY (id);


--
-- TOC entry 3923 (class 2606 OID 16950)
-- Name: order_items order_items_pkey; Type: CONSTRAINT; Schema: public; Owner: utephonehub_user
--

ALTER TABLE ONLY public.order_items
    ADD CONSTRAINT order_items_pkey PRIMARY KEY (id);


--
-- TOC entry 3917 (class 2606 OID 16931)
-- Name: orders orders_order_code_key; Type: CONSTRAINT; Schema: public; Owner: utephonehub_user
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_order_code_key UNIQUE (order_code);


--
-- TOC entry 3919 (class 2606 OID 16929)
-- Name: orders orders_pkey; Type: CONSTRAINT; Schema: public; Owner: utephonehub_user
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (id);


--
-- TOC entry 3905 (class 2606 OID 16898)
-- Name: product_images product_images_pkey; Type: CONSTRAINT; Schema: public; Owner: utephonehub_user
--

ALTER TABLE ONLY public.product_images
    ADD CONSTRAINT product_images_pkey PRIMARY KEY (id);


--
-- TOC entry 3903 (class 2606 OID 16878)
-- Name: products products_pkey; Type: CONSTRAINT; Schema: public; Owner: utephonehub_user
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT products_pkey PRIMARY KEY (id);


--
-- TOC entry 3940 (class 2606 OID 17023)
-- Name: review_likes review_likes_pkey; Type: CONSTRAINT; Schema: public; Owner: utephonehub_user
--

ALTER TABLE ONLY public.review_likes
    ADD CONSTRAINT review_likes_pkey PRIMARY KEY (id);


--
-- TOC entry 3942 (class 2606 OID 17025)
-- Name: review_likes review_likes_user_id_review_id_key; Type: CONSTRAINT; Schema: public; Owner: utephonehub_user
--

ALTER TABLE ONLY public.review_likes
    ADD CONSTRAINT review_likes_user_id_review_id_key UNIQUE (user_id, review_id);


--
-- TOC entry 3936 (class 2606 OID 17004)
-- Name: reviews reviews_pkey; Type: CONSTRAINT; Schema: public; Owner: utephonehub_user
--

ALTER TABLE ONLY public.reviews
    ADD CONSTRAINT reviews_pkey PRIMARY KEY (id);


--
-- TOC entry 3938 (class 2606 OID 17006)
-- Name: reviews reviews_user_id_product_id_key; Type: CONSTRAINT; Schema: public; Owner: utephonehub_user
--

ALTER TABLE ONLY public.reviews
    ADD CONSTRAINT reviews_user_id_product_id_key UNIQUE (user_id, product_id);


--
-- TOC entry 3886 (class 2606 OID 16827)
-- Name: users users_email_key; Type: CONSTRAINT; Schema: public; Owner: utephonehub_user
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_email_key UNIQUE (email);


--
-- TOC entry 3888 (class 2606 OID 16823)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: utephonehub_user
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 3890 (class 2606 OID 16825)
-- Name: users users_username_key; Type: CONSTRAINT; Schema: public; Owner: utephonehub_user
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_username_key UNIQUE (username);


--
-- TOC entry 3909 (class 2606 OID 16916)
-- Name: vouchers vouchers_code_key; Type: CONSTRAINT; Schema: public; Owner: utephonehub_user
--

ALTER TABLE ONLY public.vouchers
    ADD CONSTRAINT vouchers_code_key UNIQUE (code);


--
-- TOC entry 3911 (class 2606 OID 16914)
-- Name: vouchers vouchers_pkey; Type: CONSTRAINT; Schema: public; Owner: utephonehub_user
--

ALTER TABLE ONLY public.vouchers
    ADD CONSTRAINT vouchers_pkey PRIMARY KEY (id);


--
-- TOC entry 3893 (class 1259 OID 17053)
-- Name: idx_addresses_user_id; Type: INDEX; Schema: public; Owner: utephonehub_user
--

CREATE INDEX idx_addresses_user_id ON public.addresses USING btree (user_id);


--
-- TOC entry 3920 (class 1259 OID 17062)
-- Name: idx_order_items_order_id; Type: INDEX; Schema: public; Owner: utephonehub_user
--

CREATE INDEX idx_order_items_order_id ON public.order_items USING btree (order_id);


--
-- TOC entry 3921 (class 1259 OID 17063)
-- Name: idx_order_items_product_id; Type: INDEX; Schema: public; Owner: utephonehub_user
--

CREATE INDEX idx_order_items_product_id ON public.order_items USING btree (product_id);


--
-- TOC entry 3912 (class 1259 OID 17059)
-- Name: idx_orders_email; Type: INDEX; Schema: public; Owner: utephonehub_user
--

CREATE INDEX idx_orders_email ON public.orders USING btree (email);


--
-- TOC entry 3913 (class 1259 OID 17060)
-- Name: idx_orders_order_code; Type: INDEX; Schema: public; Owner: utephonehub_user
--

CREATE INDEX idx_orders_order_code ON public.orders USING btree (order_code);


--
-- TOC entry 3914 (class 1259 OID 17061)
-- Name: idx_orders_status; Type: INDEX; Schema: public; Owner: utephonehub_user
--

CREATE INDEX idx_orders_status ON public.orders USING btree (status);


--
-- TOC entry 3915 (class 1259 OID 17058)
-- Name: idx_orders_user_id; Type: INDEX; Schema: public; Owner: utephonehub_user
--

CREATE INDEX idx_orders_user_id ON public.orders USING btree (user_id);


--
-- TOC entry 3898 (class 1259 OID 17056)
-- Name: idx_products_brand_id; Type: INDEX; Schema: public; Owner: utephonehub_user
--

CREATE INDEX idx_products_brand_id ON public.products USING btree (brand_id);


--
-- TOC entry 3899 (class 1259 OID 17055)
-- Name: idx_products_category_id; Type: INDEX; Schema: public; Owner: utephonehub_user
--

CREATE INDEX idx_products_category_id ON public.products USING btree (category_id);


--
-- TOC entry 3900 (class 1259 OID 17054)
-- Name: idx_products_name; Type: INDEX; Schema: public; Owner: utephonehub_user
--

CREATE INDEX idx_products_name ON public.products USING btree (name);


--
-- TOC entry 3901 (class 1259 OID 17057)
-- Name: idx_products_status; Type: INDEX; Schema: public; Owner: utephonehub_user
--

CREATE INDEX idx_products_status ON public.products USING btree (status);


--
-- TOC entry 3932 (class 1259 OID 17064)
-- Name: idx_reviews_product_id; Type: INDEX; Schema: public; Owner: utephonehub_user
--

CREATE INDEX idx_reviews_product_id ON public.reviews USING btree (product_id);


--
-- TOC entry 3933 (class 1259 OID 17066)
-- Name: idx_reviews_rating; Type: INDEX; Schema: public; Owner: utephonehub_user
--

CREATE INDEX idx_reviews_rating ON public.reviews USING btree (rating);


--
-- TOC entry 3934 (class 1259 OID 17065)
-- Name: idx_reviews_user_id; Type: INDEX; Schema: public; Owner: utephonehub_user
--

CREATE INDEX idx_reviews_user_id ON public.reviews USING btree (user_id);


--
-- TOC entry 3882 (class 1259 OID 17050)
-- Name: idx_users_email; Type: INDEX; Schema: public; Owner: utephonehub_user
--

CREATE INDEX idx_users_email ON public.users USING btree (email);


--
-- TOC entry 3883 (class 1259 OID 17052)
-- Name: idx_users_role; Type: INDEX; Schema: public; Owner: utephonehub_user
--

CREATE INDEX idx_users_role ON public.users USING btree (role);


--
-- TOC entry 3884 (class 1259 OID 17051)
-- Name: idx_users_username; Type: INDEX; Schema: public; Owner: utephonehub_user
--

CREATE INDEX idx_users_username ON public.users USING btree (username);


--
-- TOC entry 3906 (class 1259 OID 17067)
-- Name: idx_vouchers_code; Type: INDEX; Schema: public; Owner: utephonehub_user
--

CREATE INDEX idx_vouchers_code ON public.vouchers USING btree (code);


--
-- TOC entry 3907 (class 1259 OID 17068)
-- Name: idx_vouchers_status; Type: INDEX; Schema: public; Owner: utephonehub_user
--

CREATE INDEX idx_vouchers_status ON public.vouchers USING btree (status);


--
-- TOC entry 3943 (class 2606 OID 16838)
-- Name: addresses addresses_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: utephonehub_user
--

ALTER TABLE ONLY public.addresses
    ADD CONSTRAINT addresses_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE;


--
-- TOC entry 3953 (class 2606 OID 16985)
-- Name: cart_items cart_items_cart_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: utephonehub_user
--

ALTER TABLE ONLY public.cart_items
    ADD CONSTRAINT cart_items_cart_id_fkey FOREIGN KEY (cart_id) REFERENCES public.carts(id) ON DELETE CASCADE;


--
-- TOC entry 3954 (class 2606 OID 16990)
-- Name: cart_items cart_items_product_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: utephonehub_user
--

ALTER TABLE ONLY public.cart_items
    ADD CONSTRAINT cart_items_product_id_fkey FOREIGN KEY (product_id) REFERENCES public.products(id) ON DELETE CASCADE;


--
-- TOC entry 3952 (class 2606 OID 16970)
-- Name: carts carts_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: utephonehub_user
--

ALTER TABLE ONLY public.carts
    ADD CONSTRAINT carts_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE;


--
-- TOC entry 3944 (class 2606 OID 16852)
-- Name: categories categories_parent_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: utephonehub_user
--

ALTER TABLE ONLY public.categories
    ADD CONSTRAINT categories_parent_id_fkey FOREIGN KEY (parent_id) REFERENCES public.categories(id) ON DELETE RESTRICT;


--
-- TOC entry 3950 (class 2606 OID 16951)
-- Name: order_items order_items_order_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: utephonehub_user
--

ALTER TABLE ONLY public.order_items
    ADD CONSTRAINT order_items_order_id_fkey FOREIGN KEY (order_id) REFERENCES public.orders(id) ON DELETE CASCADE;


--
-- TOC entry 3951 (class 2606 OID 16956)
-- Name: order_items order_items_product_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: utephonehub_user
--

ALTER TABLE ONLY public.order_items
    ADD CONSTRAINT order_items_product_id_fkey FOREIGN KEY (product_id) REFERENCES public.products(id) ON DELETE RESTRICT;


--
-- TOC entry 3948 (class 2606 OID 16932)
-- Name: orders orders_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: utephonehub_user
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE SET NULL;


--
-- TOC entry 3949 (class 2606 OID 16937)
-- Name: orders orders_voucher_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: utephonehub_user
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_voucher_id_fkey FOREIGN KEY (voucher_id) REFERENCES public.vouchers(id) ON DELETE SET NULL;


--
-- TOC entry 3947 (class 2606 OID 16899)
-- Name: product_images product_images_product_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: utephonehub_user
--

ALTER TABLE ONLY public.product_images
    ADD CONSTRAINT product_images_product_id_fkey FOREIGN KEY (product_id) REFERENCES public.products(id) ON DELETE CASCADE;


--
-- TOC entry 3945 (class 2606 OID 16884)
-- Name: products products_brand_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: utephonehub_user
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT products_brand_id_fkey FOREIGN KEY (brand_id) REFERENCES public.brands(id) ON DELETE RESTRICT;


--
-- TOC entry 3946 (class 2606 OID 16879)
-- Name: products products_category_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: utephonehub_user
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT products_category_id_fkey FOREIGN KEY (category_id) REFERENCES public.categories(id) ON DELETE RESTRICT;


--
-- TOC entry 3957 (class 2606 OID 17031)
-- Name: review_likes review_likes_review_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: utephonehub_user
--

ALTER TABLE ONLY public.review_likes
    ADD CONSTRAINT review_likes_review_id_fkey FOREIGN KEY (review_id) REFERENCES public.reviews(id) ON DELETE CASCADE;


--
-- TOC entry 3958 (class 2606 OID 17026)
-- Name: review_likes review_likes_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: utephonehub_user
--

ALTER TABLE ONLY public.review_likes
    ADD CONSTRAINT review_likes_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE;


--
-- TOC entry 3955 (class 2606 OID 17012)
-- Name: reviews reviews_product_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: utephonehub_user
--

ALTER TABLE ONLY public.reviews
    ADD CONSTRAINT reviews_product_id_fkey FOREIGN KEY (product_id) REFERENCES public.products(id) ON DELETE CASCADE;


--
-- TOC entry 3956 (class 2606 OID 17007)
-- Name: reviews reviews_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: utephonehub_user
--

ALTER TABLE ONLY public.reviews
    ADD CONSTRAINT reviews_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE;


--
-- TOC entry 4137 (class 0 OID 0)
-- Dependencies: 5
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: pg_database_owner
--

GRANT ALL ON SCHEMA public TO cloudsqlsuperuser;


-- Completed on 2025-10-15 05:46:56

--
-- PostgreSQL database dump complete
--

\unrestrict f0U3NkO3gLT2TyDeaO6sxNSujJai0kd8fsYQaERJzs7cditjt0uhbLKK40cad7f

