import { Spacing } from '@fun-eat/design-system';

import { ProductOverviewItem } from '@/components/Product';
import { useProductRankingQuery } from '@/hooks/queries/rank';
import displaySlice from '@/utils/displaySlice';

interface ProductRankingListProps {
  isHomePage?: boolean;
}

const ProductRankingList = ({ isHomePage }: ProductRankingListProps) => {
  const { data: productRankings } = useProductRankingQuery();
  const productsToDisplay = displaySlice(isHomePage, productRankings?.products, 3);

  return (
    <ul>
      {productsToDisplay?.map(({ id, name, image }, index) => (
        <li key={id}>
          <ProductOverviewItem rank={index + 1} name={name} image={image} />
          <Spacing size={16} />
        </li>
      ))}
    </ul>
  );
};

export default ProductRankingList;
