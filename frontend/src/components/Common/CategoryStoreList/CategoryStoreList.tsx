import { Link } from '@fun-eat/design-system';
import { Link as RouterLink } from 'react-router-dom';
import styled from 'styled-components';

import CategoryItem from '../CategoryItem/CategoryItem';

import { CATEGORY_TYPE } from '@/constants';
import { useGA } from '@/hooks/common';
import { useCategoryStoreQuery } from '@/hooks/queries/product';

const category = CATEGORY_TYPE.STORE;

const CategoryStoreList = () => {
  const { data: categories } = useCategoryStoreQuery(category);
  const { gaEvent } = useGA();

  const handleHomeCategoryLinkClick = (categoryName: string) => {
    gaEvent({
      category: 'link',
      action: `${categoryName} 카테고리 링크 클릭`,
      label: '카테고리',
    });
  };

  return (
    <div>
      <CategoryStoreListWrapper>
        {categories.map((menu) => (
          <Link
            key={menu.id}
            as={RouterLink}
            to={`products/store?category=${menu.id}`}
            onClick={() => handleHomeCategoryLinkClick(menu.name)}
          >
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
