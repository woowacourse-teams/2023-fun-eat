import type { Tag } from './common';

export interface Product {
  id: number;
  name: string;
  price: number;
  image: string | null;
  averageRating: number;
  reviewCount: number;
}

export interface ProductDetail {
  id: number;
  name: string;
  price: number;
  image: string | null;
  content: string;
  averageRating: number;
  reviewCount: number;
  bookmark: boolean;
  tags: Tag[];
}

export interface PBProduct {
  id: number;
  name: string;
  price: number;
  image: string | null;
  averageRating: number;
  reviewCount?: number;
}
