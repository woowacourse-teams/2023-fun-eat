import ProductRankingItem from '../ProductOverviewItem/ProductOverviewItem';

import productRanking from '@/mocks/data/productRanking.json';

const ProductRankingList = () => {
  return (
    <ul>
      {productRanking.map((productRanking) => (
        <li key={productRanking.id}>
          <ProductRankingItem productRanking={productRanking} variant="ranking" />
        </li>
      ))}
    </ul>
  );
};

export default ProductRankingList;
