import productImage from './mock_img.jpg';

export interface Product {
  id: number;
  name: string;
  price: number;
  image: string;
  averageRating: number;
  reviewCount: number;
}

export const MOCK_PRODUCTS: Product[] = [
  {
    id: 1,
    name: '꼬북칩',
    price: 1500,
    image: productImage,
    averageRating: 4.5,
    reviewCount: 100,
  },
  {
    id: 2,
    name: '새우깡',
    price: 1000,
    image: productImage,
    averageRating: 4.0,
    reviewCount: 55,
  },
];
