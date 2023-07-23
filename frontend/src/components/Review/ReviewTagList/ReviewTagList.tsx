import { Badge, Heading, theme } from '@fun-eat/design-system';
import styled from 'styled-components';

import reviewTagList from '@/mocks/data/reviewTagList.json';

const ReviewTagList = () => {
  return (
    <ReviewTagListContainer>
      <Heading as="h2" size="lg">
        상품에 관한 태그를 선택해주세요 (3개)
      </Heading>
      <ul>
        {reviewTagList.map(({ id, content }) => (
          <TagItem key={id}>
            <Badge color={theme.colors.primary} textColor={theme.textColors.default}>
              {content}
            </Badge>
          </TagItem>
        ))}
      </ul>
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

const TagItem = styled.li`
  display: inline-block;
  margin: 4px 8px;
`;
