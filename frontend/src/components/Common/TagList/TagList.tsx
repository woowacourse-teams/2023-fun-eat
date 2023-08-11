import { Badge } from '@fun-eat/design-system';
import styled from 'styled-components';

import type { Tag } from '@/types/common';
import { convertTagColor } from '@/utils/convertTagColor';

interface TagListProps {
  tags: Tag[];
}

const TagList = ({ tags }: TagListProps) => {
  return (
    <TagListContainer>
      {tags.map((tag) => {
        const tagColor = convertTagColor(tag.tagType);
        return (
          <li key={tag.id}>
            <TagBadge color={tagColor} textColor="black">
              {tag.name}
            </TagBadge>
          </li>
        );
      })}
    </TagListContainer>
  );
};

export default TagList;

const TagListContainer = styled.ul`
  display: flex;
  column-gap: 8px;
`;

const TagBadge = styled(Badge)`
  font-weight: bold;
`;
