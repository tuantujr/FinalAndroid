const mongoose = require('mongoose');
const dotenv = require('dotenv');
const Category = require('./src/models/Category');
const Product = require('./src/models/Product');
const User = require('./src/models/User');
const bcrypt = require('bcrypt');

dotenv.config();

mongoose.connect(process.env.MONGODB_URI)
    .then(() => console.log('MongoDB Connected for Seeding'))
    .catch(err => console.log(err));

const seedData = async () => {
    try {
        // Clear existing data
        await Category.deleteMany({});
        await Product.deleteMany({});
        await User.deleteMany({});

        console.log('Cleared database');

        // Create Categories
        const categories = await Category.insertMany([
            { title: 'Vegetables', picUrl: 'https://cdn-icons-png.flaticon.com/512/2329/2329903.png' },
            { title: 'Fruits', picUrl: 'https://cdn-icons-png.flaticon.com/512/1625/1625048.png' },
            { title: 'Dairy', picUrl: 'https://cdn-icons-png.flaticon.com/512/2674/2674486.png' },
            { title: 'Meat', picUrl: 'https://cdn-icons-png.flaticon.com/512/1046/1046769.png' }
        ]);

        console.log('Categories created');

        // Create Products
        const products = await Product.insertMany([
            {
                title: 'Bell Pepper Red',
                description: 'Fresh red bell pepper, rich in Vitamin C.',
                picUrl: ['https://freepngimg.com/thumb/pepper/10-red-pepper-png-image-thumb.png'],
                price: 5.99,
                oldPrice: 7.99,
                rating: 4.5,
                categoryId: categories[0]._id
            },
            {
                title: 'Carrot',
                description: 'Organic carrots, perfect for salads.',
                picUrl: ['https://freepngimg.com/thumb/carrot/1-carrot-png-image-thumb.png'],
                price: 2.99,
                oldPrice: 3.50,
                rating: 4.2,
                categoryId: categories[0]._id
            },
            {
                title: 'Banana',
                description: 'Sweet and ripe bananas.',
                picUrl: ['https://freepngimg.com/thumb/banana/24-banana-png-image-thumb.png'],
                price: 1.99,
                oldPrice: 2.50,
                rating: 4.8,
                categoryId: categories[1]._id
            },
            {
                title: 'Apple',
                description: 'Crisp red apples.',
                picUrl: ['https://freepngimg.com/thumb/apple/1-apple-png-image-thumb.png'],
                price: 3.99,
                oldPrice: 4.99,
                rating: 4.6,
                categoryId: categories[1]._id
            },
            {
                title: 'Milk',
                description: 'Fresh whole milk.',
                picUrl: ['https://freepngimg.com/thumb/milk/5-milk-bottle-png-image-thumb.png'],
                price: 4.50,
                oldPrice: 5.00,
                rating: 4.7,
                categoryId: categories[2]._id
            },
            {
                title: 'Beef Steak',
                description: 'Premium beef steak.',
                picUrl: ['https://freepngimg.com/thumb/meat/12-meat-png-image-thumb.png'],
                price: 15.99,
                oldPrice: 18.99,
                rating: 4.9,
                categoryId: categories[3]._id
            }
        ]);

        console.log('Products created');

        // Create Test User
        const salt = await bcrypt.genSalt(10);
        const hashedPassword = await bcrypt.hash('123456', salt);
        await User.create({
            name: 'Test User',
            username: 'testuser',
            email: 'test@example.com',
            password: hashedPassword // Manually hashing since we might bypass pre-save hook or just to be safe
        });

        console.log('Test User created (username: testuser, email: test@example.com, pass: 123456)');

        process.exit();
    } catch (error) {
        console.error(error);
        process.exit(1);
    }
};

seedData();
