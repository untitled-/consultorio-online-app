'use strict';

angular.module('consultorioOnlineAppApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('symptom', {
                parent: 'entity',
                url: '/symptoms',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'consultorioOnlineAppApp.symptom.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/symptom/symptoms.html',
                        controller: 'SymptomController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('symptom');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('symptom.detail', {
                parent: 'entity',
                url: '/symptom/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'consultorioOnlineAppApp.symptom.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/symptom/symptom-detail.html',
                        controller: 'SymptomDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('symptom');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Symptom', function($stateParams, Symptom) {
                        return Symptom.get({id : $stateParams.id});
                    }]
                }
            })
            .state('symptom.new', {
                parent: 'symptom',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/symptom/symptom-dialog.html',
                        controller: 'SymptomDialogController',
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
                        $state.go('symptom', null, { reload: true });
                    }, function() {
                        $state.go('symptom');
                    })
                }]
            })
            .state('symptom.edit', {
                parent: 'symptom',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/symptom/symptom-dialog.html',
                        controller: 'SymptomDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Symptom', function(Symptom) {
                                return Symptom.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('symptom', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('symptom.delete', {
                parent: 'symptom',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/symptom/symptom-delete-dialog.html',
                        controller: 'SymptomDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Symptom', function(Symptom) {
                                return Symptom.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('symptom', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
