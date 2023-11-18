    package com.hcmute.ecommerce.universeshop.category;

    import com.hcmute.ecommerce.universeshop.base.exception.Constants;
    import com.hcmute.ecommerce.universeshop.base.exception.ErrorMessage;
    import com.hcmute.ecommerce.universeshop.base.exception.InputValidationException;
    import com.hcmute.ecommerce.universeshop.base.exception.ResourceNotFoundException;
    import lombok.AllArgsConstructor;
    import org.springframework.stereotype.Service;

    import java.util.List;
    @AllArgsConstructor
    @Service
    public class CategoryServiceImp implements CategoryService{
        private final ErrorMessage errorMessage;
        private final CategoryRepository categoryRepository;
        @Override
        public List<CategoryEntity> findAll() {
            return categoryRepository.findAll();
        }

        @Override
        public CategoryEntity save(CategoryEntity category) {
            if(isNull(category)) {
                throw new InputValidationException(errorMessage.getMessage(Constants.CONTENT_MUST_NOT_BE_NULL));
            }
            if(isExistByName(category.getName())) {
                throw new InputValidationException(errorMessage.getMessage(Constants.CATEGORY_NAME_EXISTED));
            }
            return  categoryRepository.save(category);
        }
        @Override
        public CategoryEntity findById(Long id) {
            return categoryRepository.findById(id).get();
        }
        @Override
        public CategoryEntity update(CategoryEntity category, Long id) {
            CategoryEntity categoryEntity = categoryRepository.findById(id).get();
            if(categoryEntity == null) throw new InputValidationException(errorMessage.getMessage(Constants.CATEGORY_NOT_FOUND));
            if(!categoryEntity.getName().equals(category.getName()) && isExistByName(category.getName()))
                throw new InputValidationException(errorMessage.getMessage(Constants.CATEGORY_NAME_EXISTED));
            categoryEntity.setName(category.getName());
            categoryEntity.setImage(category.getImage());

            return categoryRepository.save(categoryEntity);
        }
        @Override
        public void deleteById(Long id) {
            if(isExistById(id)) {
                categoryRepository.deleteById(id);
                return;
            }
            throw new ResourceNotFoundException(errorMessage.getMessage(Constants.CATEGORY_NOT_FOUND));
        }
        public Boolean isExistByName(String name) {
            return categoryRepository.getCategoryByName(name) != null;
        }
        public Boolean isExistById(Long id) {
            return categoryRepository.findById(id) != null;
        }
        public Boolean isNull (CategoryEntity category) {
            return category.getName() == null ||
                    category.getImage() == null;
        }
    }
