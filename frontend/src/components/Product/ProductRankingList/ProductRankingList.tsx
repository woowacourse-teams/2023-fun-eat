import { Spacing } from '@fun-eat/design-system';

import ProductOverviewItem from '../ProductOverviewItem/ProductOverviewItem';

import productRanking from '@/mocks/data/productRanking.json';

const ProductRankingList = () => {
  return (
    <ul>
      {productRanking.map((productRanking) => (
        <li key={productRanking.id}>
          <ProductOverviewItem rank={productRanking.rank} name={productRanking.name} image={productRanking.image} />
          <Spacing size={16} />
        </li>
      ))}
    </ul>
  );
};

export default ProductRankingList;
