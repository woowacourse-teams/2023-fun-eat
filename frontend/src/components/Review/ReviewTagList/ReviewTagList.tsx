import { Badge, Heading, theme } from '@fun-eat/design-system';
import { useState } from 'react';
import styled, { css } from 'styled-components';

import { SvgIcon } from '@/components/Common';
import reviewTagList from '@/mocks/data/reviewTagList.json';

const MAX_DISPLAYED_TAGS = 8;
const MAX_SELECTED_TAGS = 3;

const ReviewTagList = () => {
  const [maxDisplayedTags, setMaxDisplayedTags] = useState(MAX_DISPLAYED_TAGS);
  const [selectedTags, setSelectedTags] = useState<number[]>([]);

  const showMoreTags = () => {
    setMaxDisplayedTags(maxDisplayedTags + MAX_DISPLAYED_TAGS);
  };

  const toggleTagSelection = (id: number, isSelected: boolean) => {
    if (selectedTags.length >= MAX_SELECTED_TAGS && !isSelected) return;
    setSelectedTags((prevSelectedTags) => {
      const isSelected = prevSelectedTags.includes(id);
      if (isSelected) {
        return prevSelectedTags.filter((selectedTag) => selectedTag !== id);
      }
      return [...prevSelectedTags, id];
    });
  };

  const renderTag = (id: number, content: string) => {
    const isSelected = selectedTags.includes(id);

    const tagStyles = css`
      ${isSelected && `border: 1px solid ${theme.colors.information};`}
      ${selectedTags.length >= MAX_SELECTED_TAGS && !isSelected && `opacity: 0.5;`}
    `;

    return (
      <TagItem key={id} onClick={() => toggleTagSelection(id, isSelected)}>
        <Badge color={theme.colors.primary} textColor={theme.textColors.default} css={tagStyles}>
          {content}
        </Badge>
      </TagItem>
    );
  };

  return (
    <ReviewTagListContainer>
      <Heading as="h2" size="lg">
        상품에 관한 태그를 선택해주세요 (3개)
      </Heading>
      <TagListWrapper>
        {reviewTagList.slice(0, maxDisplayedTags).map(({ id, content }) => renderTag(id, content))}
      </TagListWrapper>
      {reviewTagList.length > maxDisplayedTags && (
        <button onClick={showMoreTags}>
          <SvgIcon
            variant="arrow"
            width={15}
            css={`
              transform: rotate(270deg);
            `}
          />
        </button>
      )}
    </ReviewTagListContainer>
  );
};

export default ReviewTagList;

const ReviewTagListContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 20px;
`;

const TagListWrapper = styled.ul`
  text-align: center;
`;

const TagItem = styled.li`
  display: inline-block;
  margin: 4px 8px;
`;
