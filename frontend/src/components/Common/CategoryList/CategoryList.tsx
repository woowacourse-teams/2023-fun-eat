import { Link } from '@fun-eat/design-system';
import { Link as RouterLink } from 'react-router-dom';
import styled from 'styled-components';

import CategoryItem from '../CategoryItem/CategoryItem';

import { CATEGORY_TYPE } from '@/constants';
import { useCategoryQuery } from '@/hooks/queries/product';

interface CategoryListProps {
  menuVariant: keyof typeof CATEGORY_TYPE;
}

const CategoryList = ({ menuVariant }: CategoryListProps) => {
  const { data: categories } = useCategoryQuery(CATEGORY_TYPE[menuVariant]);

  return (
    <CategoryListContainer>
      <CategoryListWrapper>
        {categories.map((menu) => (
          <Link key={menu.id} as={RouterLink} to={`products/${menuVariant.toLowerCase()}?category=${menu.id}`}>
            <CategoryItem name={menu.name} image={menu.image} />
          </Link>
        ))}
      </CategoryListWrapper>
    </CategoryListContainer>
  );
};

export default CategoryList;

const CategoryListContainer = styled.div`
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

const CategoryListWrapper = styled.div`
  display: flex;
  gap: 20px;
`;
