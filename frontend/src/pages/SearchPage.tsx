import { Heading, Spacing } from '@fun-eat/design-system';
import styled from 'styled-components';

import { Input, SvgIcon, TabMenu } from '@/components/Common';
import { SearchedList } from '@/components/Search';

const SearchPage = () => {
  return (
    <>
      <Input customWidth="100%" placeholder="상품 이름을 검색해보세요." rightIcon={<SvgIcon variant="search" />} />
      <Spacing size={20} />
      <TabMenu tabMenus={['상품', '꿀조합']} />
      <SearchResultSection>
        <Heading as="h2" size="lg" weight="regular">
          <MarkedText>&apos;담곰이&apos;</MarkedText>에 대한 검색결과입니다.
        </Heading>
        <Spacing size={20} />
        <SearchedList />
      </SearchResultSection>
    </>
  );
};

export default SearchPage;

const SearchResultSection = styled.section`
  margin-top: 30px;
`;

const MarkedText = styled.mark`
  background-color: ${({ theme }) => theme.backgroundColors.default};
  font-weight: ${({ theme }) => theme.fontWeights.bold};
`;
