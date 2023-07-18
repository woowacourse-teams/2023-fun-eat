import { Badge } from '@fun-eat/design-system';
import styled from 'styled-components';

import type { Tag } from '@/types/common';

interface TagListProps {
  tags: Tag[];
}

const TagList = ({ tags }: TagListProps) => {
  return (
    <TagListContainer>
      {tags.map((tag) => (
        <li key={tag.id}>
          <Badge color="black" textColor="white">
            {tag.name}
          </Badge>
        </li>
      ))}
    </TagListContainer>
  );
};

export default TagList;

const TagListContainer = styled.ul`
  display: flex;
  column-gap: 8px;
`;
