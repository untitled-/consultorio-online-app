'use strict';

angular.module('consultorioOnlineUiApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('disease', {
                parent: 'entity',
                url: '/diseases',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'consultorioOnlineUiApp.disease.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/disease/diseases.html',
                        controller: 'DiseaseController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('disease');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('disease.detail', {
                parent: 'entity',
                url: '/disease/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'consultorioOnlineUiApp.disease.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/disease/disease-detail.html',
                        controller: 'DiseaseDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('disease');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Disease', function($stateParams, Disease) {
                        return Disease.get({id : $stateParams.id});
                    }]
                }
            })
            .state('disease.new', {
                parent: 'disease',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/disease/disease-dialog.html',
                        controller: 'DiseaseDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    code: null,
                                    name: null,
                                    description: null,
                                    type: null,
                                    isCongenital: null,
                                    isInfectious: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('disease', null, { reload: true });
                    }, function() {
                        $state.go('disease');
                    })
                }]
            })
            .state('disease.edit', {
                parent: 'disease',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/disease/disease-dialog.html',
                        controller: 'DiseaseDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Disease', function(Disease) {
                                return Disease.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('disease', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('disease.delete', {
                parent: 'disease',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/disease/disease-delete-dialog.html',
                        controller: 'DiseaseDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Disease', function(Disease) {
                                return Disease.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('disease', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
