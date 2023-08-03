import { Spacing } from '@fun-eat/design-system';

import { ProductOverviewItem } from '@/components/Product';
import type { ProductRanking } from '@/types/ranking';

interface ProductRankingListProps {
  productRankings: ProductRanking[];
}

const ProductRankingList = ({ productRankings }: ProductRankingListProps) => {
  return (
    <ul>
      {productRankings.map(({ id, name, image }, index) => (
        <li key={id}>
          <ProductOverviewItem rank={index + 1} name={name} image={image} />
          <Spacing size={16} />
        </li>
      ))}
    </ul>
  );
};

export default ProductRankingList;
