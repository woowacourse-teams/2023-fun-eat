export interface RankingProduct {
  id: number;
  rank: number;
  name: string;
  image: string;
}

export interface ReviewRanking {
  reviewId: number;
  productId: number;
  productName: string;
  content: string;
  rating: number;
  favoriteCount: number;
}
