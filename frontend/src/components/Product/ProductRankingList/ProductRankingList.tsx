import ProductRankingItem from '../ProductRankingItem/ProductRankingItem';

import productRanking from '@/mocks/data/productRanking.json';

const ProductRankingList = () => {
  return (
    <ul>
      {productRanking.map((productRanking) => (
        <li key={productRanking.id}>
          <ProductRankingItem productRanking={productRanking} />
        </li>
      ))}
    </ul>
  );
};

export default ProductRankingList;
