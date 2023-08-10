import { Spacing } from '@fun-eat/design-system';
import { useLocation } from 'react-router-dom';

import { ProductOverviewItem } from '@/components/Product';
import { useProductRankingQuery } from '@/hooks/queries/rank';

const ProductRankingList = () => {
  const location = useLocation();
  const isRootPath = location.pathname === '/';

  const { data: productRankings } = useProductRankingQuery();
  const productsToDisplay = isRootPath ? productRankings?.products.slice(0, 3) : productRankings?.products;

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
