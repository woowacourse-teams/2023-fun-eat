import RankingProductItem from '@/components/RankingProductItem/RankingProductItem';
import rankingProducts from '@/mocks/data/rankingProducts.json';

const RankingProductList = () => {
  return (
    <ul>
      {rankingProducts.map((rankingProduct) => (
        <li key={rankingProduct.id}>
          <RankingProductItem rankingProduct={rankingProduct} />
        </li>
      ))}
    </ul>
  );
};

export default RankingProductList;
