import { Link } from '@fun-eat/design-system';
import { Link as RouterLink } from 'react-router-dom';
import styled from 'styled-components';

import CategoryItem from '../CategoryItem/CategoryItem';

import { CATEGORY_TYPE } from '@/constants';
import { useCategoryStoreQuery } from '@/hooks/queries/product';

const category = CATEGORY_TYPE.STORE;

const CategoryStoreList = () => {
  const { data: categories } = useCategoryStoreQuery(category);

  return (
    <div>
      <CategoryStoreListWrapper>
        {categories.map((menu) => (
          <Link key={menu.id} as={RouterLink} to={`products/store?category=${menu.id}`}>
            <CategoryItem name={menu.name} image={menu.image} />
          </Link>
        ))}
      </CategoryStoreListWrapper>
    </div>
  );
};

export default CategoryStoreList;

const CategoryStoreListWrapper = styled.div`
  display: flex;
  gap: 16px;
`;
