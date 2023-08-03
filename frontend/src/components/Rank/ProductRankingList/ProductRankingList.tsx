import { Spacing } from '@fun-eat/design-system';

import { ProductOverviewItem } from '@/components/Product';
import type { ProductRanking } from '@/types/ranking';

interface ProductRankingListProps {
  productRanking: ProductRanking[];
}

const ProductRankingList = ({ productRanking }: ProductRankingListProps) => {
  return (
    <ul>
      {productRanking.map(({ id, name, image }, idx) => (
        <li key={id}>
          <ProductOverviewItem rank={idx + 1} name={name} image={image} />
          <Spacing size={16} />
        </li>
      ))}
    </ul>
  );
};

export default ProductRankingList;
