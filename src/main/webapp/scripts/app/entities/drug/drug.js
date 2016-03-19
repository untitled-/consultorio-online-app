'use strict';

angular.module('consultorioOnlineUiApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('drug', {
                parent: 'entity',
                url: '/drugs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'consultorioOnlineUiApp.drug.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/drug/drugs.html',
                        controller: 'DrugController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('drug');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('drug.detail', {
                parent: 'entity',
                url: '/drug/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'consultorioOnlineUiApp.drug.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/drug/drug-detail.html',
                        controller: 'DrugDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('drug');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Drug', function($stateParams, Drug) {
                        return Drug.get({id : $stateParams.id});
                    }]
                }
            })
            .state('drug.new', {
                parent: 'drug',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/drug/drug-dialog.html',
                        controller: 'DrugDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    code: null,
                                    name: null,
                                    description: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('drug', null, { reload: true });
                    }, function() {
                        $state.go('drug');
                    })
                }]
            })
            .state('drug.edit', {
                parent: 'drug',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/drug/drug-dialog.html',
                        controller: 'DrugDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Drug', function(Drug) {
                                return Drug.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('drug', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('drug.delete', {
                parent: 'drug',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/drug/drug-delete-dialog.html',
                        controller: 'DrugDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Drug', function(Drug) {
                                return Drug.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('drug', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
