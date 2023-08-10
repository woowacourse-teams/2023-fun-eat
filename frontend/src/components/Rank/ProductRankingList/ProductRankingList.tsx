import { Spacing } from '@fun-eat/design-system';

import { ProductOverviewItem } from '@/components/Product';
import { PATH } from '@/constants/path';
import { useProductRankingQuery } from '@/hooks/queries/rank';
import useDisplaySlice from '@/hooks/useDisplaySlice';

const ProductRankingList = () => {
  const { data: productRankings } = useProductRankingQuery();
  const productsToDisplay = useDisplaySlice(PATH.HOME, productRankings?.products, 3);

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
