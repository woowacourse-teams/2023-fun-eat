import RankingProductItem from '@components/RankingProductItem/RankingProductItem';
import rankingProducts from '@mocks/data/rankingProducts.json';

const RankingProductList = () => {
  return (
    <ul>
      {rankingProducts.map((rankingProduct) => (
        <RankingProductItem key={rankingProduct.id} rankingProduct={rankingProduct} />
      ))}
    </ul>
  );
};

export default RankingProductList;
