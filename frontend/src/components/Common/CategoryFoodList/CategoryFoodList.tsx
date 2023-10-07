import { Link } from '@fun-eat/design-system';
import { Link as RouterLink } from 'react-router-dom';
import styled from 'styled-components';

import CategoryItem from '../CategoryItem/CategoryItem';

import { CATEGORY_TYPE } from '@/constants';
import { useCategoryFoodQuery } from '@/hooks/queries/product';

const category = CATEGORY_TYPE.FOOD;

const CategoryFoodList = () => {
  const { data: categories } = useCategoryFoodQuery(category);

  return (
    <CategoryFoodListWrapper>
      {categories.map((menu) => (
        <Link key={menu.id} as={RouterLink} to={`products/food?category=${menu.id}`}>
          <CategoryItem name={menu.name} image={menu.image} />
        </Link>
      ))}
    </CategoryFoodListWrapper>
  );
};

export default CategoryFoodList;

const CategoryFoodListWrapper = styled.div`
  display: flex;
  gap: 16px;
`;
