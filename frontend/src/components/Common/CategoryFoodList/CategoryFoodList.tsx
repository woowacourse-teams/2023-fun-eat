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
    <CategoryFoodListContainer>
      <CategoryFoodListWrapper>
        {categories.map((menu) => (
          <Link key={menu.id} as={RouterLink} to={`products/food?category=${menu.id}`}>
            <CategoryItem name={menu.name} image={menu.image} />
          </Link>
        ))}
      </CategoryFoodListWrapper>
    </CategoryFoodListContainer>
  );
};

export default CategoryFoodList;

const CategoryFoodListContainer = styled.div`
  overflow-x: auto;
  overflow-y: hidden;

  @media screen and (min-width: 500px) {
    display: flex;
    flex-direction: column;
    align-items: center;
  }

  &::-webkit-scrollbar {
    display: none;
  }
`;

const CategoryFoodListWrapper = styled.div`
  display: flex;
  gap: 20px;
`;
