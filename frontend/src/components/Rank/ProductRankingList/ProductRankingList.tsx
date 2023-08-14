import { Link, Spacing } from '@fun-eat/design-system';
import { Link as RouterLink } from 'react-router-dom';

import { ProductOverviewItem } from '@/components/Product';
import { PATH } from '@/constants/path';
import { useProductRankingQuery } from '@/hooks/queries/rank';
import displaySlice from '@/utils/displaySlice';

interface ProductRankingListProps {
  isHomePage?: boolean;
}

const ProductRankingList = ({ isHomePage }: ProductRankingListProps) => {
  const { data: productRankings } = useProductRankingQuery();
  const productsToDisplay = displaySlice(isHomePage, productRankings.products, 3);

  return (
    <ul>
      {productsToDisplay.map(({ id, name, image, categoryType }, index) => (
        <li key={id}>
          <Link as={RouterLink} to={`${PATH.PRODUCT_LIST}/${categoryType}/${id}`}>
            <ProductOverviewItem rank={index + 1} name={name} image={image} />
          </Link>
          <Spacing size={16} />
        </li>
      ))}
    </ul>
  );
};

export default ProductRankingList;
